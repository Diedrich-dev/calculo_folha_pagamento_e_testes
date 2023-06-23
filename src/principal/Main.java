package principal;

import controle.ControleCargo;
import controle.ControleConfiguracaoFolhaPag;
import controle.ControlePessoa;
import persistencia.ConexaoBD;

public class Main {
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            ConexaoBD.getConection();
            ControlePessoa controlP = new ControlePessoa();
            //controlP.cadastrar();
            ControleCargo controlCargo = new ControleCargo();
            //controlCargo.cadastrar();
//            ControleConfiguracaoFolhaPag controlConfiguracaoFolhaPag = new ControleConfiguracaoFolhaPag();
//            controlConfiguracaoFolhaPag.cadastrar();
        } catch (Exception ex) {
            System.err.println(""+ex.getMessage());
        }

    }
}
