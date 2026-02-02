package fachada;

import builder.Anuncio;
import config.ConfigManager;
import integration.ImportadorCSVImovel;
import memento.HistoricoAnuncio;
import model.*;
import observerAndstrategy.*;
import search.*;
import validation.*;


import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class MyHomeFachada {
    private List<Anuncio> catalogo = new ArrayList<>();
    private List<Imovel> baseImoveis = new ArrayList<>();
    private ImportadorCSVImovel importador = new ImportadorCSVImovel();
    private ConfigManager config = ConfigManager.getInstance();
    private HistoricoAnuncio historico = new HistoricoAnuncio();

    public String inicializar() {
        try {
            this.baseImoveis = importador.importar("imoveis.csv");
            return "[FACHADA] Sistema inicializado. " + baseImoveis.size() + " imóveis carregados.";
        } catch (IOException e) {
            return "[FACHADA] Erro ao carregar base: " + e.getMessage();
        }
    }

    public Imovel getImovelDaBase(int index) {
        if (index >= 0 && index < baseImoveis.size()) {
            return baseImoveis.get(index).clone();
        }
        return null;
    }


    public List<String> cadastrarAnuncioRapido(Anunciante dono, String titulo, double preco, Imovel imovel, List<Comprador> interessados) {
        List<String> logs = new ArrayList<>();
        
        Anuncio anuncio = new Anuncio.Builder()
                .comAnunciante(dono).comTitulo(titulo)
                .comPreco(preco).paraImovel(imovel).build();
        

        anuncio.adicionarCanal(new NotificadorTelegram());
        anuncio.adicionarCanal(new NotificadorEmail());
        anuncio.adicionarCanal(new NotificadorWhatsApp());
        

        for (Comprador c : interessados) {
            logs.add(anuncio.favoritar(c));
        }
        
        catalogo.add(anuncio);
        logs.add("[FACHADA] Cadastro concluído com " + interessados.size() + " observadores iniciais.");
        
        return logs;
    }

    public List<String> processarPublicacao(Anuncio anuncio) {
        List<String> logs = new ArrayList<>();
        ValidadorAnuncio corrente = new ValidadorPreco();
        corrente.setProximo(new ValidadorTermosProibidos());

        if (corrente.validar(anuncio)) {
            logs.add("[FACHADA] Validação técnica OK.");
            logs.addAll(anuncio.solicitarPublicacao()); // Rascunho -> Moderando
            logs.addAll(anuncio.solicitarPublicacao()); // Moderando -> Ativo
        } else {
            logs.add("[FACHADA] Erro: Anúncio reprovado na validação.");
        }
        return logs;
    }

    public List<Anuncio> buscar(String termo, double precoMax) {
        FiltroComposto filtro = new FiltroComposto();
        filtro.adicionar(new FiltroTitulo(termo));
        filtro.adicionar(new FiltroPrecoMaximo(precoMax));
        
        List<Anuncio> resultados = new ArrayList<>();
        for (Anuncio a : catalogo) {
            if (filtro.filtrar(a)) resultados.add(a);
        }
        return resultados;
    }

    public List<Anuncio> getCatalogo() { return catalogo; }
    public String getTelegramID() { return config.getConfig("telegram.chatid"); }


    public void salvarEstado(Anuncio anuncio) {
        historico.guardar(anuncio.criarSnapshot());
    }

    public String desfazerAlteracao(Anuncio anuncio) {
        anuncio.restaurar(historico.desfazer());
        return "[MEMENTO] Estado anterior restaurado com sucesso.";
    }
}