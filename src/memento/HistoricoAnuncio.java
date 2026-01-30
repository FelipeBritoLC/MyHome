package memento;

import java.util.Stack;

public class HistoricoAnuncio {
    private Stack<AnuncioMemento> estados = new Stack<>();

    public void guardar(AnuncioMemento memento) {
        estados.push(memento);
    }

    public AnuncioMemento desfazer() {
        if (!estados.isEmpty()) {
            return estados.pop();
        }
        return null;
    }
}
