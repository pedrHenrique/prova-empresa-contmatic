package br.com.contmatic.empresav2.util;

import java.util.Random;

import br.com.contmatic.model.v2.Contato;
import br.com.contmatic.model.v2.Departamento;
import br.com.contmatic.model.v2.Empresa;
import br.com.contmatic.model.v2.Endereco;
import br.com.contmatic.model.v2.Estado;
import br.com.contmatic.model.v2.Funcionario;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class FixtureTempleateLoader implements TemplateLoader {

    private static final String[] DATAS = {"25/07/2029", "15/05/1971", "14/04/1959", "24/12/1968", "14/06/1999", "02/02/1990"};    
    private static final String[] CONTATOS = {"\\d{10}", "\\d{2}9\\d{8}"};
    private static final String[] CONTATOS1 = {"\\d{10}", "\\d{2}9\\d{8}", "\\d{2} 9\\d{8}"};//, "\\(??\\)\\d{4,5}\\-\\d{4}"
    private static final String[] CONTATOS2 = {"\\d{10}", "\\d{2}9\\d{8}", "\\d{2} 9\\d{8}", ""};
    private static final String[] EMAIL = {"Teste\\w{3,6}@cont.com", "PhoenixTest\\w{1,6}@contmatic.com.br", "Paulo_\\w{1,8}@outlook.com", "Ana\\w{1,7}@gmail.com"};//email não pode ser repetido
    private static final String[] NUMINDENTIFICADOR = {"\\d{1}[A-Z]{1}", "\\d{2}[A-Z]{1}", "\\d{2}[A-Z]{1}" , "\\d{3}", "\\d{3}[A-Z]{1}", "\\d{4}", "\\d{4}[A-Z]{1}"};
    
    @Override
    public void load() {
        Fixture.of(Departamento.class).addTemplate("valido", new Rule(){         
            {
            add("idDepartamento", random(Long.class, range(10L, 299L)));
            add("nome", random("Diretória", "Expedição", "Contábil", "Recepção","Segurança", "Qualidade", "Infraestrutura", "Estoque", "Markting", "TI", "Infraestrutura"));
            add("ramal", random(Integer.class, range(1, 999)));
        }});       
        
        Fixture.of(Empresa.class).addTemplate("valido", new Rule(){ 
            {
            add("idEmpresa", random(Long.class, range(10L, 499L))); 
            add("nome", random("Yahoo!", "AMD", "Clube do Hardware", "Dafiti", "Tifany&CO", "Mercedez-Benz", "Amazon.com, Itaú", "HLC"));
            add("cnpj", cnpj());
            add("cep", regex("\\d{5}-\\d{3}"));
            //add("dtFundacao", DATAS[new Random().nextInt(DATAS.length)]); //enquanto getDtFundacao tiver tipo de retorno DateTime, está forma do fixture não vai funcionar
            add("contato", random("28211740711", "10749174420", "2558693247", "4448956789", "testeMatic@cont.com", "PhoenixTest@contmatic.com"));
        }});
        
        Fixture.of(Funcionario.class).addTemplate("valido", new Rule(){ 
            {
            add("idFuncionario", random(Long.class, range(20L, 3000L))); 
            add("nome", name());
            add("cpf", regex("\\d{3}.\\d{3}.\\d{3}-\\d{2}")); // ->  Poderia ser dessa forma também d{11} d{3}.\\d{3}.\\d{3}-\\d{2}
            add("endereco", one(Endereco.class, "valido"));            
            add("contato", regex(CONTATOS [new Random().nextInt(CONTATOS.length)]));
            add("salario", uniqueRandom(800.00, 900.00, 1000.00, 1500.00, 2500.00, 3000.00, 4500.00, 6000.00));
            add("departamento", one(Departamento.class, "valido"));
            
            
        }});
        
        Fixture.of(Endereco.class).addTemplate("valido", new Rule(){ //Exemplo Endereco com valores validos. Porém, nem todos correspondem com suas dévidas localizações.          
            {
            add("rua", random("Rua dos Lírios", "Rua Primeiro de Maio", "Rua H12B", "Travessa Niterói", "Rua Projetada 899", "Rua Nove", "Quadra 25", "Rua Florentino Moreira", "Rua Mauro Fecury")); 
            add("bairro", random("Vila União", "Paraíso", "Parque Jardim Santanense", "Maruípe", "Setor Habitacional Samambaia (Vicente Pires)", "Santo Antônio", "Bela Vista", "Indústrias"));
            add("num", regex(NUMINDENTIFICADOR [new Random().nextInt(NUMINDENTIFICADOR.length)]));
            add("cep", regex("\\d{5}-\\d{3}")); //<- Não pode ser usado pelo ViaCep
            add("cidade", random("Teresina", "Lages", "Cariacica", "Parnamirim", "Florianópolis", "Nova Iguaçu", "Boa Vista", "Campo Grande", "Belo Horizonte", "Aracaju", "João Pessoa"));            
            add("estado", random(Estado.class));            
            
        }});  

        Fixture.of(Contato.class).addTemplate("valido", new Rule(){ //Exemplo Endereco com valores validos. Porém, nem todos correspondem com suas dévidas localizações.          
            {
            add("contato1", regex(CONTATOS1 [new Random().nextInt(CONTATOS1.length)]));
            add("contato2", regex(CONTATOS2 [new Random().nextInt(CONTATOS2.length)]));
            add("email", regex(EMAIL [new Random().nextInt(EMAIL.length)]));
        }});  
        
        //Nem todos os valores do funcionário sempre seram incorretos, tirando seu endereço
        Fixture.of(Funcionario.class).addTemplate("invalido", new Rule(){ 
            {
            add("idFuncionario", random(Long.class, range(-500L, 100L))); 
            add("nome", random("PRËMØNÏÇÃØ", "Ｎｅｖｅ", "M丹XOM", "Ɛᴢe₡ŁA҉N҉", "A͢͢͢ℓᴇx☘", "ロロノア・ゾロ ","             "));  //gerador a partir: https://nickfinder.com/
            add("cpf", regex("\\w{11}")); 
            add("endereco", new Endereco());            
            add("contato", random("28211740711", "5511749174420", "055119448956789", "j@com." ));
            add("salario", uniqueRandom(0.0, -800.00, -1000.00));
            add("departamento", one(Departamento.class, "invalido"));
            
            
        }});
        
        Fixture.of(Endereco.class).addTemplate("invalido", new Rule(){ //Exemplo Endereco com valores validos. Porém, nem todos correspondem com suas dévidas localizações.          
            {
            add("rua", random("Яuд ŞãØ mдЯcØŞ", "X", "яuค dค Ъคหคหค", "૨ષα ℓષíż Բ૨αżѳท", "rυα вєirα riσ")); 
            add("bairro", random("NULL","Nada", "UmNomeDeUmBairroRealmenteMuitoLongoPffNaoUtilizeUmNomeComoEsse", "ɔɛɴ†ʀѳ", "Ｊ丹尺刀工爪 刀丹ち 口ㄥ工Vモ工尺丹ち", "[̲̅J̲̅α̲̅я̲̅d̲̅i̲̅м̲̅ ̲̅т̲̅я̲̅σ̲̅ρ̲̅i̲̅c̲̅α̲̅l̲̅]"));
            add("num", random("", "CINCO", "dois", "UmOtimoNumeroDeIdentificacaoAqui", "teste"));
            add("cep", random("KKKKKKK-KKK")); 
            add("cidade", random("", "roça", "ExemploDeNomeDeCidadeMuitoGrandeMesmoUaU", "ⓒⓤⓘⓐⓑⓐ", "匕モ下é"));  
            add("estado", random(Estado.class));            
            
        }});
        
        Fixture.of(Departamento.class).addTemplate("invalido", new Rule(){
            {
            add("idDepartamento", random(Long.class, range(300L, 500L))); 
            add("nome", random("⦃fiɹᵉ⦄", "⚡𐐚𐍉u⚡", "[][][][]][", "チキンパステル", "ロロノア・ゾロ ","             "));
            add("ramal", random(Integer.class, range(999, 2000)));
        }});

        Fixture.of(Contato.class).addTemplate("invalido", new Rule(){ //Exemplo Endereco com valores validos. Porém, nem todos correspondem com suas dévidas localizações.          
            {
            add("contato1", random("(AB)CDEF-GHIJ", "Um Exemplo de Número errado aqui", "(--)-----------", "40028922", "123")); 
            add("contato2", random("(XB)JKFEW-IUYS", "555596478045", "(--)-----------", "40028922", "45679"));
            add("email", random("jb@.br", "carlosAlberto.com.br", "umNomeGenerico@", "joaninha@dominiuinventado.omc"));
        }});
    }   
 

    //Método (provisório) criado para retorno literal da String. Ao Inves de um DateTime 
    public static String getData() {
        return DATAS[new Random().nextInt(DATAS.length)];
    }        
}
