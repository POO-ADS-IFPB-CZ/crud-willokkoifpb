import view.TelaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Garante que a interface gráfica seja criada na thread de eventos do Swing (Event Dispatch Thread - EDT)
        // Esta é a forma correta e mais segura de iniciar aplicações Swing.
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}