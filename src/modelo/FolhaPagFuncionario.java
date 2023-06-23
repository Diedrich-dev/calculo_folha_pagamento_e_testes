package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class FolhaPagFuncionario extends Entidade  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(value = 2000, message = "O ano de referência não pode ser anterior a 2000")
    @Max(value = 2030, message = "O ano de referência não pode ser posterior a 2030")
    @NotNull(message = "O ano de referência não pode ser nulo")
    private Integer anoReferencia;

    @NotNull(message = "O mês de referência não pode ser nulo")
    @Min(value = 1, message = "O mês de referência deve ser um valor maior ou igual a 1 ")
    @Max(value = 12, message = "O mês de referência deve ser um valor maior ou igual a 12")
    private Integer mesReferencia;

    @NotNull(message = "A data de pagamento não pode ser nula")
    private LocalDate dataPagamento;

    @Min(value = 1, message = "As horas trabalhadas deve ser maior que zero")
    @Max(value = 300, message = "As horas trabalhadas deve ser menor ou igual que 300")
    private int horasTrabalhadas;

    @Min(value = 0, message = "O número de faltas sem justificativa não pode ser negativo")
    @Max(value = 30, message = "O número de faltas sem justificativa não pode ser maior que 30")
    private int faltasSemJustificativa;


    @PositiveOrZero(message = "Salário Base Não pode ser negativo")
    private double salarioBase;

    @PositiveOrZero(message = "O valor de Horas Extras Não pode ser negativo")
    private double valorHorasExtra;

    @PositiveOrZero(message = "O total de Proventos Não pode ser negativo")
    private double totalProventos;

    @PositiveOrZero(message = "O valor do vale-transporte  Não pode ser negativo")
    private double valorValeTransporte;

    @PositiveOrZero(message = "O valor do vale-alimentação Não pode ser negativo")
    private double valorValeAlimentacao;

    @PositiveOrZero(message = "valorINSS Não pode ser negativo")
    private double valorINSS;

    @PositiveOrZero(message = "descontoIR Não pode ser negativo")
    private double descontoIR;

    @PositiveOrZero(message = "descontoValeTransporte Não pode ser negativo")
    private double descontoValeTransporte;

    @PositiveOrZero(message = "descontoValeAlimentacao Não pode ser negativo")
    private double descontoValeAlimentacao;

    @PositiveOrZero(message = "valorFGTS Não pode ser negativo")
    private double valorFGTS;

    @PositiveOrZero(message = "totalDescontos Não pode ser negativo")
    private double totalDescontos;

    @PositiveOrZero(message = "salarioLiquido Não pode ser negativo")
    private double salarioLiquido;

    @NotNull
    @ManyToOne(optional = false)
    private Funcionario funcionario;

    @NotNull(message = "Folha de Pagamento não pode ser nula")
    @ManyToOne(optional = false)
    private ConfiguracaoFolhaPag configuracaoFolhaPag;

    public FolhaPagFuncionario() {
    }

    public FolhaPagFuncionario(Integer id, Integer anoReferencia, Integer mesReferencia, LocalDate dataPagamento, int horasTrabalhadas, int faltasSemJustificativa, Funcionario funcionario, ConfiguracaoFolhaPag configuracaoFolhaPag) {
        this.id = id;
        this.anoReferencia = anoReferencia;
        this.mesReferencia = mesReferencia;
        this.dataPagamento = dataPagamento;
        this.horasTrabalhadas = horasTrabalhadas;
        this.faltasSemJustificativa = faltasSemJustificativa;
        this.funcionario = funcionario;
        this.configuracaoFolhaPag = configuracaoFolhaPag;
    }
    public FolhaPagFuncionario(Integer id, Integer anoReferencia, Integer mesReferencia, LocalDate dataPagamento, int horasTrabalhadas, int faltasSemJustificativa, double salarioBase, double valorHorasExtra, double totalProventos, double valorValeTransporte, double valorValeAlimentacao, double valorINSS, double descontoIR, double descontoValeTransporte, double descontoValeAlimentacao, double valorFGTS, double totalDescontos, double salarioLiquido, Funcionario funcionario, ConfiguracaoFolhaPag configuracaoFolhaPag) {
        this.id = id;
        this.anoReferencia = anoReferencia;
        this.mesReferencia = mesReferencia;
        this.dataPagamento = dataPagamento;
        this.horasTrabalhadas = horasTrabalhadas;
        this.faltasSemJustificativa = faltasSemJustificativa;
        this.salarioBase = salarioBase;
        this.valorHorasExtra = valorHorasExtra;
        this.totalProventos = totalProventos;
        this.valorValeTransporte = valorValeTransporte;
        this.valorValeAlimentacao = valorValeAlimentacao;
        this.valorINSS = valorINSS;
        this.descontoIR = descontoIR;
        this.descontoValeTransporte = descontoValeTransporte;
        this.descontoValeAlimentacao = descontoValeAlimentacao;
        this.valorFGTS = valorFGTS;
        this.totalDescontos = totalDescontos;
        this.salarioLiquido = salarioLiquido;
        this.funcionario = funcionario;
        this.configuracaoFolhaPag = configuracaoFolhaPag;
    }



    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnoReferencia() {
        return anoReferencia;
    }

    public void setAnoReferencia(Integer anoReferencia) {
        this.anoReferencia = anoReferencia;
    }

    public Integer getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(Integer mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public int getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(int horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public int getFaltasSemJustificativa() {
        return faltasSemJustificativa;
    }

    public void setFaltasSemJustificativa(int faltasSemJustificativa) {
        this.faltasSemJustificativa = faltasSemJustificativa;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public double getValorHorasExtra() {
        return valorHorasExtra;
    }

    public void setValorHorasExtra(double valorHorasExtra) {
        this.valorHorasExtra = valorHorasExtra;
    }

    public double getTotalProventos() {
        return totalProventos;
    }

    public void setTotalProventos(double totalProventos) {
        this.totalProventos = totalProventos;
    }

    public double getValorValeTransporte() {
        return valorValeTransporte;
    }

    public void setValorValeTransporte(double valorValeTransporte) {
        this.valorValeTransporte = valorValeTransporte;
    }

    public double getValorValeAlimentacao() {
        return valorValeAlimentacao;
    }

    public void setValorValeAlimentacao(double valorValeAlimentacao) {
        this.valorValeAlimentacao = valorValeAlimentacao;
    }

    public double getValorINSS() {
        return valorINSS;
    }

    public void setValorINSS(double valorINSS) {
        this.valorINSS = valorINSS;
    }

    public double getDescontoIR() {
        return descontoIR;
    }

    public void setDescontoIR(double descontoIR) {
        this.descontoIR = descontoIR;
    }

    public double getDescontoValeTransporte() {
        return descontoValeTransporte;
    }

    public void setDescontoValeTransporte(double descontoValeTransporte) {
        this.descontoValeTransporte = descontoValeTransporte;
    }

    public double getDescontoValeAlimentacao() {
        return descontoValeAlimentacao;
    }

    public void setDescontoValeAlimentacao(double descontoValeAlimentacao) {
        this.descontoValeAlimentacao = descontoValeAlimentacao;
    }

    public double getValorFGTS() {
        return valorFGTS;
    }

    public void setValorFGTS(double valorFGTS) {
        this.valorFGTS = valorFGTS;
    }

    public double getTotalDescontos() {
        return totalDescontos;
    }

    public void setTotalDescontos(double totalDescontos) {
        this.totalDescontos = totalDescontos;
    }

    public double getSalarioLiquido() {
        return salarioLiquido;
    }

    public void setSalarioLiquido(double salarioLiquido) {
        this.salarioLiquido = salarioLiquido;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public ConfiguracaoFolhaPag getConfiguracaoFolhaPag() {
        return configuracaoFolhaPag;
    }

    public void setConfiguracaoFolhaPag(ConfiguracaoFolhaPag configuracaoFolhaPag) {
        this.configuracaoFolhaPag = configuracaoFolhaPag;
    }



    @Override
    public String toString() {
        return "FolhaPagFuncionario{" + "id=" + id + ", anoReferencia=" + anoReferencia + ", mesReferencia=" + mesReferencia + ", dataPagamento=" + dataPagamento + ", horasTrabalhadas=" + horasTrabalhadas + ", faltasSemJustificativa=" + faltasSemJustificativa + ", salarioBase=" + salarioBase + ", valorHorasExtra=" + valorHorasExtra + ", totalProventos=" + totalProventos + ", valorValeTransporte=" + valorValeTransporte + ", valorValeAlimentacao=" + valorValeAlimentacao + ", valorINSS=" + valorINSS + ", descontoIR=" + descontoIR + ", descontoValeTransporte=" + descontoValeTransporte + ", descontoValeAlimentacao=" + descontoValeAlimentacao + ", valorFGTS=" + valorFGTS + ", totalDescontos=" + totalDescontos + ", salarioLiquido=" + salarioLiquido + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.anoReferencia);
        hash = 67 * hash + Objects.hashCode(this.mesReferencia);
        hash = 67 * hash + Objects.hashCode(this.funcionario);
        hash = 67 * hash + Objects.hashCode(this.configuracaoFolhaPag);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FolhaPagFuncionario other = (FolhaPagFuncionario) obj;
        if (this.horasTrabalhadas != other.horasTrabalhadas) {
            return false;
        }
        if (this.faltasSemJustificativa != other.faltasSemJustificativa) {
            return false;
        }
        if (Double.doubleToLongBits(this.salarioBase) != Double.doubleToLongBits(other.salarioBase)) {
            return false;
        }
        if (Double.doubleToLongBits(this.valorHorasExtra) != Double.doubleToLongBits(other.valorHorasExtra)) {
            return false;
        }
        if (Double.doubleToLongBits(this.totalProventos) != Double.doubleToLongBits(other.totalProventos)) {
            return false;
        }
        if (Double.doubleToLongBits(this.valorValeTransporte) != Double.doubleToLongBits(other.valorValeTransporte)) {
            return false;
        }
        if (Double.doubleToLongBits(this.valorValeAlimentacao) != Double.doubleToLongBits(other.valorValeAlimentacao)) {
            return false;
        }
        if (Double.doubleToLongBits(this.valorINSS) != Double.doubleToLongBits(other.valorINSS)) {
            return false;
        }
        if (Double.doubleToLongBits(this.descontoIR) != Double.doubleToLongBits(other.descontoIR)) {
            return false;
        }
        if (Double.doubleToLongBits(this.descontoValeTransporte) != Double.doubleToLongBits(other.descontoValeTransporte)) {
            return false;
        }
        if (Double.doubleToLongBits(this.descontoValeAlimentacao) != Double.doubleToLongBits(other.descontoValeAlimentacao)) {
            return false;
        }
        if (Double.doubleToLongBits(this.valorFGTS) != Double.doubleToLongBits(other.valorFGTS)) {
            return false;
        }
        if (Double.doubleToLongBits(this.totalDescontos) != Double.doubleToLongBits(other.totalDescontos)) {
            return false;
        }
        if (Double.doubleToLongBits(this.salarioLiquido) != Double.doubleToLongBits(other.salarioLiquido)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.anoReferencia, other.anoReferencia)) {
            return false;
        }
        if (!Objects.equals(this.mesReferencia, other.mesReferencia)) {
            return false;
        }
        if (!Objects.equals(this.dataPagamento, other.dataPagamento)) {
            return false;
        }
        if (!Objects.equals(this.funcionario, other.funcionario)) {
            return false;
        }
        return Objects.equals(this.configuracaoFolhaPag, other.configuracaoFolhaPag);
    }


}

