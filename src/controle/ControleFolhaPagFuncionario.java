package controle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import modelo.FolhaPagFuncionario;
import persistencia.DaoFolhaPagFuncionario;
import util.Input;
import util.NumberUtils;
import util.validacoes.ValidacaoUtil;

/**
 *
 * @author Andre
 */
public class ControleFolhaPagFuncionario {
     private DaoFolhaPagFuncionario dao;
     private ControleFuncionario controleFuncionario;
     private ControleConfiguracaoFolhaPag controleConfiguracaoFolhaPag;
    private ValidacaoUtil validator = new ValidacaoUtil(FolhaPagFuncionario.class);

    public ControleFolhaPagFuncionario() {
        dao = new DaoFolhaPagFuncionario();
        controleFuncionario = new ControleFuncionario();
        controleConfiguracaoFolhaPag = new  ControleConfiguracaoFolhaPag();
    }

    public void cadastrar() {

        FolhaPagFuncionario folhaPag = new FolhaPagFuncionario();
        setarDados(folhaPag);
        try {
            if (validator.validarEntidade(folhaPag)) {
                dao.saveOrUpdate(folhaPag);
            }
        } catch (Exception e) {
            validator.msgErroCadastro(e, "salvar");
        }

    }

    public void editar() {
        FolhaPagFuncionario folhaPag = pesquisar();
        setarDados(folhaPag);
        try {
            if (validator.validarEntidade(folhaPag)) {
                dao.saveOrUpdate(folhaPag);
            }
        } catch (Exception e) {
            validator.msgErroCadastro(e, "editar");
        }

    }
           

    public void setarDados(FolhaPagFuncionario folha) {
        System.out.println("informe o ano de referência da folha de pagamento: ");
        folha.setAnoReferencia(Input.nextInt());
        System.out.println("informe o mês de referência da folha de pagamento: ");
        folha.setMesReferencia(Input.nextInt());
        System.out.println("configuração da folha de pagamento: ");
        folha.setConfiguracaoFolhaPag(controleConfiguracaoFolhaPag.pesquisar());
        System.out.println("informe a data de pagamento: ");
        folha.setDataPagamento(Input.nextLocalDate());
        System.out.println("funcionário: ");
        folha.setFuncionario(controleFuncionario.pesquisar());
        System.out.println("informe a quantidade de horas trabalhadas pelo funcionário no mês: ");
        folha.setHorasTrabalhadas(Input.nextInt());
        System.out.println("informe a quantidade de faltas sem justificativa do funcionário no mês: ");
        folha.setFaltasSemJustificativa(Input.nextInt());
    }

    /**
     * Função que realiza o cálculo de folha de pagamento para um determinado funcionário.
     * A folha de pagamento consiste nos seguintes cálculos:
     * Valor de horas extras:
     * Valor total de Proventos:
     * Valor vale-transporte:
     * Valor vale-alimentação:
     * Valor de desconto do vale-transporte:
     * Valor de desconto do vale-alimentação:
     * Valor de desconto INSS:
     * Valor de desconto IR:
     * Valor total de Descontos
     * Valor do sálario Líquido:
     * Valor do FGTS:
     * @param folha o parâmetro folha deverá conter as informações de configuração da folha de pagamento, ano e mês de referência,
     *              data prevista para pagamento, o funciánario, a quantidade de horas trabalhadas no mês e a quantidade de faltas sem justificativa.
     * @see
     * <a href="https://blog.genialinvestimentos.com.br/salario-liquido-e-bruto/"> como calcular salário líquido</a>
     * <a href="https://valorinveste.globo.com/ferramentas/calculadoras/calculadora-salario-liquido/">calculadora-salario-liquido</a>
     */
//    public void calcularFolhadePagamento(FolhaPagFuncionario folha){
//        //Implementar o método para calcular folha de pagamento.
//
//        folha.setSalarioBase(folha.getFuncionario().getSalario());
//
//        //Para calcular o valor da hora deve-se dividir o salário pela carga horária mensal ( salário/cargahorariaMensal)
//        double valorHora = NumberUtils.arredondar(folha.getSalarioBase()/ folha.getFuncionario().getCargo().getCargaHorariaMensal(), 2);
//        double valorhorasFaltantes = 0;
//        double valorDescontoFaltasSemJustificativa = 0;
//
//        //Verifica se quantidade de horas tabalhadas é maior que carga horária mensal, caso seja verdadeiro, o funcionário tem direito a horas extras
//        if(folha.getHorasTrabalhadas() > folha.getFuncionario().getCargo().getCargaHorariaMensal()){
//           // obter a quantidade de horas extras
//            double qtdhorasExtras = folha.getHorasTrabalhadas() - folha.getFuncionario().getCargo().getCargaHorariaMensal();
//            //calcular horas extras 50%
//            double valorHorasExtras = (qtdhorasExtras * valorHora) * 1.5;
//            folha.setValorHorasExtra(NumberUtils.arredondar(valorHorasExtras, 2));
//
//        }else {
//            // calcular às horas das faltas sem justificativa
//            int horasFaltasSemJustificativa = folha.getFaltasSemJustificativa() * 9;
//
//            //acrescentar as horas das faltas sem justificativas as horas trabalhadas, pois as faltas serão descontadas em outro cálculo mais adiante.
//            if((folha.getHorasTrabalhadas() + horasFaltasSemJustificativa) < folha.getFuncionario().getCargo().getCargaHorariaMensal()){ // verifica se o funcinário tem horas faltantes
//                // obter a quantidade de horas faltantes
//                double qtdhorasFaltantes = folha.getFuncionario().getCargo().getCargaHorariaMensal() - folha.getHorasTrabalhadas();
//                // calcula o valor de desconto das horas faltantes
//                valorhorasFaltantes = NumberUtils.arredondar((qtdhorasFaltantes * valorHora) , 2);
//            }
//        }
//
//        //Verifica se há faltas sem justificativas
//        if(folha.getFaltasSemJustificativa() > 0){
//            //calcula o valor do dia
//            double valorDia = NumberUtils.arredondar(folha.getSalarioBase() / 30, 2);
//            // calcula o valor de desconto das faltas, (mulutiplica os dias pelo valor do dia vezes 2, para descontar o dia que faltou e o DSR )
//            valorDescontoFaltasSemJustificativa = NumberUtils.arredondar( (folha.getFaltasSemJustificativa() * valorDia * 2), 2);
//        }
//
//        //calcula o total de proventos
//        double totalProventos = (folha.getSalarioBase() - valorhorasFaltantes - valorDescontoFaltasSemJustificativa) + folha.getValorHorasExtra() ;
//        totalProventos = NumberUtils.arredondar(totalProventos, 2);
//        folha.setTotalProventos(totalProventos);
//
//        //calcular desconto do INSS
////      Salário de Contribuição (R$)	Alíquota (%)	Parcela a Deduzir
////      até R$ 1.320,00	                    7,5 %	            –
////      de R$ 1.320,01 até R$ 2.571,29	    9,0 %	        19,80
////      de R$ 2.571,30 até R$ 3.856,94	   12,0 %	        96,94
////      de R$ 3.856,95 até R$ 7.507,49	   14,0 %	        174,08
//
//        double valorINSS = 0;
//        if(totalProventos <= 1320) {
//            valorINSS =  totalProventos * 0.075;
//        }
//        else if(totalProventos < 2571.30) {
//            valorINSS = (totalProventos * 0.09) - 19.80;
//        }
//        else if(totalProventos < 3856.95) {
//            valorINSS = (totalProventos * 0.12) - 96.94;
//        }
//        else if(totalProventos >= 3856.95) {
//            if(totalProventos > 7507.49){ // verifica se o total de proventos é superior ao teto de contribuição
//                valorINSS = (7507.49 * 0.14) - 174.08;
//            } else {
//                valorINSS = (totalProventos * 0.14) - 174.08;
//            }
//        }
//        folha.setValorINSS(NumberUtils.arredondar(valorINSS, 2));
//
//        double ValorBaseCalculoIR = NumberUtils.arredondar((totalProventos - folha.getValorINSS()), 2);
//    }

    public void calcularFolhadePagamento(FolhaPagFuncionario folha) {
        folha.setSalarioBase(folha.getFuncionario().getSalario());
        BigDecimal salarioHora = calcularSalarioHora(new BigDecimal(folha.getSalarioBase()), new BigDecimal(folha.getHorasTrabalhadas()));
        BigDecimal horasExtras = new BigDecimal(folha.getHorasTrabalhadas() - folha.getFuncionario().getCargo().getCargaHorariaMensal());
        int horasFaltasSemJustificativa = folha.getFaltasSemJustificativa() * 9;
        BigDecimal valorHorasFaltantes = BigDecimal.ZERO;
        BigDecimal valorDescontoFaltasSemJustificativa = BigDecimal.ZERO;

        if ((folha.getHorasTrabalhadas() + horasFaltasSemJustificativa) < folha.getFuncionario().getCargo().getCargaHorariaMensal()) {
            BigDecimal qtdHorasFaltantes = new BigDecimal(folha.getFuncionario().getCargo().getCargaHorariaMensal() - folha.getHorasTrabalhadas());
            valorHorasFaltantes = qtdHorasFaltantes.multiply(salarioHora).setScale(2, RoundingMode.HALF_UP);
        }

        if (folha.getFaltasSemJustificativa() > 0) {
            BigDecimal valorDia = new BigDecimal(folha.getSalarioBase()).divide(new BigDecimal(30), 2, RoundingMode.HALF_UP);
            valorDescontoFaltasSemJustificativa = valorDia.multiply(new BigDecimal(folha.getFaltasSemJustificativa() * 2)).setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal valorHorasExtras = calcularValorHorasExtras((horasExtras.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO : horasExtras), salarioHora);
        BigDecimal salarioBruto = new BigDecimal(folha.getSalarioBase()).add(valorHorasExtras);
        BigDecimal provendos = calcularProvendos(new BigDecimal(folha.getSalarioBase()), valorHorasExtras);
        BigDecimal valeTransporte = calcularValeTransporte(new BigDecimal(folha.getSalarioBase()));
        BigDecimal valeAlimentacao = calcularValeAlimentacao(new BigDecimal(folha.getSalarioBase()));
        BigDecimal descontoVT = calcularDescontoVT(new BigDecimal(folha.getSalarioBase()), valeTransporte);
        BigDecimal descontoVA = calcularDescontoVA(new BigDecimal(folha.getSalarioBase()), valeAlimentacao);
        BigDecimal baseCalculoINSS = calcularBaseCalculoINSS(salarioBruto, descontoVT, descontoVA);
        BigDecimal descontoINSS = calcularDescontoINSS(baseCalculoINSS);
        BigDecimal baseCalculoIR = calcularBaseCalculoIR(baseCalculoINSS, descontoINSS);
        BigDecimal descontoIR = calcularDescontoIR(baseCalculoIR);
        BigDecimal descontos = calcularDescontos(descontoVT, descontoVA, descontoINSS, descontoIR, valorHorasFaltantes, valorDescontoFaltasSemJustificativa);
        BigDecimal salarioLiquido = calcularSalarioLiquido(provendos, descontos);
        BigDecimal fgts = calcularFGTS(new BigDecimal(folha.getSalarioBase()));

        folha.setTotalProventos(salarioBruto.doubleValue());
        folha.setValorValeAlimentacao(valeAlimentacao.doubleValue());
        folha.setValorValeTransporte(valeTransporte.doubleValue());
        folha.setValorHorasExtra(valorHorasExtras.doubleValue());
        folha.setValorINSS(descontoINSS.doubleValue());
        folha.setDescontoIR(descontoIR.doubleValue());
        folha.setDescontoValeAlimentacao(descontoVA.doubleValue());
        folha.setDescontoValeTransporte(descontoVT.doubleValue());
        folha.setValorFGTS(fgts.doubleValue());
        folha.setTotalDescontos(descontos.doubleValue());
        folha.setSalarioLiquido(salarioLiquido.doubleValue());

    }

    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1320.00");
    private static final BigDecimal LIMITE_INSS_FAIXA_1 = new BigDecimal("1320.00");
    private static final BigDecimal LIMITE_INSS_FAIXA_2 = new BigDecimal("2571.29");
    private static final BigDecimal LIMITE_INSS_FAIXA_3 = new BigDecimal("3865.94");
    private static final BigDecimal LIMITE_INSS_FAIXA_4 = new BigDecimal("7507.49");
    private static final BigDecimal LIMITE_IR_FAIXA_1 = new BigDecimal("1903.98");
    private static final BigDecimal LIMITE_IR_FAIXA_2 = new BigDecimal("2826.65");
    private static final BigDecimal LIMITE_IR_FAIXA_3 = new BigDecimal("3751.05");
    private static final BigDecimal LIMITE_IR_FAIXA_4 = new BigDecimal("4664.68");
    private static final BigDecimal ALIQUOTA_INSS_FAIXA_1 = new BigDecimal("0.075");
    private static final BigDecimal ALIQUOTA_INSS_FAIXA_2 = new BigDecimal("0.09");
    private static final BigDecimal ALIQUOTA_INSS_FAIXA_3 = new BigDecimal("0.12");
    private static final BigDecimal ALIQUOTA_INSS_FAIXA_4 = new BigDecimal("0.14");
    private static final BigDecimal ALIQUOTA_IR_FAIXA_1 = new BigDecimal("0.075");
    private static final BigDecimal ALIQUOTA_IR_FAIXA_2 = new BigDecimal("0.15");
    private static final BigDecimal ALIQUOTA_IR_FAIXA_3 = new BigDecimal("0.225");
    private static final BigDecimal ALIQUOTA_IR_FAIXA_4 = new BigDecimal("0.275");
    private static final BigDecimal ALIQUOTA_FGTS = new BigDecimal("0.08");
    private static final BigDecimal ALIQUOTA_VT = new BigDecimal("0.06");
    private static final BigDecimal ALIQUOTA_VA = new BigDecimal("0.05");

    private BigDecimal calcularSalarioHora(BigDecimal salarioBase, BigDecimal horasTrabalhadasMes) {
        return salarioBase.divide(horasTrabalhadasMes, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularValorHorasExtras(BigDecimal horasExtras, BigDecimal salarioHora) {
        BigDecimal percentualHoraExtra = new BigDecimal("1.5"); // Percentual de acréscimo para horas extras (50%)
        return horasExtras.multiply(salarioHora).multiply(percentualHoraExtra);
    }

    private BigDecimal calcularProvendos(BigDecimal salarioBase, BigDecimal valorHorasExtras) {
        return salarioBase.add(valorHorasExtras);
    }

    private BigDecimal calcularValeTransporte(BigDecimal salarioBase) {
        BigDecimal percentualVT = ALIQUOTA_VT; // Percentual do salário base destinado ao vale-transporte (6%)
        return salarioBase.multiply(percentualVT);
    }

    private BigDecimal calcularValeAlimentacao(BigDecimal salarioBase) {
        BigDecimal percentualVA = ALIQUOTA_VA; // Percentual do salário base destinado ao vale-alimentação (5%)
        return salarioBase.multiply(percentualVA);
    }

    private BigDecimal calcularDescontoVT(BigDecimal salarioBase, BigDecimal valeTransporte) {
        BigDecimal limiteDescontoVT = salarioBase.multiply(new BigDecimal("0.6")); // Limite máximo de desconto do vale-transporte (60% do salário base)
        if (valeTransporte.compareTo(limiteDescontoVT) > 0) {
            return limiteDescontoVT;
        }
        return valeTransporte;
    }

    private BigDecimal calcularDescontoVA(BigDecimal salarioBase, BigDecimal valeAlimentacao) {
        BigDecimal limiteDescontoVA = salarioBase.multiply(new BigDecimal("0.2")); // Limite máximo de desconto do vale-alimentação (20% do salário base)
        if (valeAlimentacao.compareTo(limiteDescontoVA) > 0) {
            return limiteDescontoVA;
        }
        return valeAlimentacao;
    }

    private BigDecimal calcularBaseCalculoINSS(BigDecimal salarioBruto, BigDecimal descontoVT, BigDecimal descontoVA) {
        BigDecimal baseCalculo = salarioBruto.subtract(descontoVT).subtract(descontoVA);
        if (baseCalculo.compareTo(SALARIO_MINIMO) < 0) {
            return SALARIO_MINIMO;
        }
        return baseCalculo;
    }
    private BigDecimal calcularDescontoINSS(BigDecimal baseCalculoINSS) {
        BigDecimal descontoINSS;
        if (baseCalculoINSS.compareTo(LIMITE_INSS_FAIXA_1) <= 0) {
            descontoINSS = baseCalculoINSS.multiply(ALIQUOTA_INSS_FAIXA_1);
        } else if (baseCalculoINSS.compareTo(LIMITE_INSS_FAIXA_2) <= 0) {
            BigDecimal faixa1 = LIMITE_INSS_FAIXA_1.multiply(ALIQUOTA_INSS_FAIXA_1);
            descontoINSS = faixa1.add(baseCalculoINSS.subtract(LIMITE_INSS_FAIXA_1).multiply(ALIQUOTA_INSS_FAIXA_2));
        } else if (baseCalculoINSS.compareTo(LIMITE_INSS_FAIXA_3) <= 0) {
            BigDecimal faixa1 = LIMITE_INSS_FAIXA_1.multiply(ALIQUOTA_INSS_FAIXA_1);
            BigDecimal faixa2 = LIMITE_INSS_FAIXA_2.subtract(LIMITE_INSS_FAIXA_1).multiply(ALIQUOTA_INSS_FAIXA_2);
            BigDecimal faixa3 = baseCalculoINSS.subtract(LIMITE_INSS_FAIXA_2).multiply(ALIQUOTA_INSS_FAIXA_3);
            descontoINSS = faixa1.add(faixa2).add(faixa3);
        } else {
            BigDecimal faixa1 = LIMITE_INSS_FAIXA_1.multiply(ALIQUOTA_INSS_FAIXA_1);
            BigDecimal faixa2 = LIMITE_INSS_FAIXA_2.subtract(LIMITE_INSS_FAIXA_1).multiply(ALIQUOTA_INSS_FAIXA_2);
            BigDecimal faixa3 = LIMITE_INSS_FAIXA_3.subtract(LIMITE_INSS_FAIXA_2).multiply(ALIQUOTA_INSS_FAIXA_3);
            BigDecimal faixa4 = baseCalculoINSS.subtract(LIMITE_INSS_FAIXA_3).multiply(ALIQUOTA_INSS_FAIXA_4);
            descontoINSS = faixa1.add(faixa2).add(faixa3).add(faixa4);
        }
        return descontoINSS.setScale(2, RoundingMode.HALF_UP);
    }


    private BigDecimal calcularBaseCalculoIR(BigDecimal baseCalculoINSS, BigDecimal descontoINSS) {
        return baseCalculoINSS.subtract(descontoINSS);
    }

    private BigDecimal calcularDescontoIR(BigDecimal baseCalculoIR) {
        BigDecimal descontoIR;
        if (baseCalculoIR.compareTo(LIMITE_IR_FAIXA_1) <= 0) {
            descontoIR = BigDecimal.ZERO;
        } else if (baseCalculoIR.compareTo(LIMITE_IR_FAIXA_2) <= 0) {
            descontoIR = baseCalculoIR.multiply(ALIQUOTA_IR_FAIXA_1).subtract(new BigDecimal("142.80"));
        } else if (baseCalculoIR.compareTo(LIMITE_IR_FAIXA_3) <= 0) {
            descontoIR = baseCalculoIR.multiply(ALIQUOTA_IR_FAIXA_2).subtract(new BigDecimal("354.80"));
        } else if (baseCalculoIR.compareTo(LIMITE_IR_FAIXA_4) <= 0) {
            descontoIR = baseCalculoIR.multiply(ALIQUOTA_IR_FAIXA_3).subtract(new BigDecimal("636.13"));
        } else {
            descontoIR = baseCalculoIR.multiply(ALIQUOTA_IR_FAIXA_4).subtract(new BigDecimal("869.36"));
        }
        return descontoIR.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularDescontos(BigDecimal descontoVT, BigDecimal descontoVA, BigDecimal descontoINSS, BigDecimal descontoIR, BigDecimal valorHorasFaltantes, BigDecimal valorFaltasSemJustificativa) {
        return descontoVT.add(descontoVA).add(descontoINSS).add(descontoIR).add(valorHorasFaltantes).add(valorFaltasSemJustificativa);
    }

    private BigDecimal calcularSalarioLiquido(BigDecimal provendos, BigDecimal descontos) {
        return provendos.subtract(descontos);
    }

    private BigDecimal calcularFGTS(BigDecimal salarioBase) {
        return salarioBase.multiply(ALIQUOTA_FGTS);
    }
    
    public FolhaPagFuncionario pesquisar(){
        System.out.println("informe o código da Folha de Pagamento do funcionário que deseja pesquisar: ");
        int codigo = Input.nextInt();
        return carregarPorId(codigo);
    }
    
    public FolhaPagFuncionario carregarPorId(int id) {
        return (FolhaPagFuncionario) dao.findById(id);
    }
    
    public List<FolhaPagFuncionario> carregarTodos() {
        return dao.findAll().stream().map(e -> (FolhaPagFuncionario) e).collect(Collectors.toList());
    }

    public void remover() {
        FolhaPagFuncionario folhaPag = pesquisar();
        if(folhaPag == null){
            ValidacaoUtil.msgAviso("Cadastro não encontrado", "A Folha de Pagamento do funcionário não foi encontrada na base de dados!");
        }
        dao.delete(folhaPag);
    }
}
