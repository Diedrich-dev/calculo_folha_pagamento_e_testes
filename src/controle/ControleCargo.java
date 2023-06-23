package controle;

import java.util.List;
import java.util.stream.Collectors;
import modelo.Cargo;
import persistencia.DaoCargo;
import util.Input;
import util.validacoes.ValidacaoUtil;

/**
 *
 * @author Andre
 */
public class ControleCargo {
    private DaoCargo dao;
    private ValidacaoUtil validator = new ValidacaoUtil(Cargo.class);

    public ControleCargo() {
        dao = new DaoCargo();
    }

    public void cadastrar() {

        Cargo cargo = new Cargo();
        setarDados(cargo);
        try {
            if (validator.validarEntidade(cargo)) {
                dao.saveOrUpdate(cargo);
            }
        } catch (Exception e) {
            validator.msgErroCadastro(e, "salvar");
        }

    }

    public void editar() {
        Cargo p = pesquisar();
        setarDados(p);
        try {
            if (validator.validarEntidade(p)) {
                dao.saveOrUpdate(p);
            }
        } catch (Exception e) {
            validator.msgErroCadastro(e, "editar");
        }

    }

    public void setarDados(Cargo p) {
        System.out.println("informe a descrição do cargo: ");
        p.setDescricao(Input.next());
        System.out.println("informe a carga horária mensal: ");
        p.setCargaHorariaMensal(Input.nextInt());
    
    }
    
    public Cargo pesquisar(){
        System.out.println("informe o código do cargo que deseja pesquisar: ");
        int codigo = Input.nextInt();
        return carregarPorId(codigo);
    }
    
    public Cargo carregarPorId(int id) {
        return (Cargo) dao.findById(id);
    }
    
    public List<Cargo> carregarTodos() {
        return dao.findAll().stream().map(e -> (Cargo) e).collect(Collectors.toList());
    }

    public void remover() {
        Cargo cargo = pesquisar();
        if(cargo == null){
            ValidacaoUtil.msgAviso("Cadastro não encontrado", "O cargo não foi localizado no banco de dados!");
        }
        dao.delete(cargo);
    }
}
