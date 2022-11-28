package UI;

public class Main {
    public static void main(String[] args) {
        BoilerPlateView view = new BoilerPlateView("Main");
        LoginView signIn = new LoginView(view.getLogic());
    }
}
