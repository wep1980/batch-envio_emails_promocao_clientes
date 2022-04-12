package br.com.wepdev.enviopromocoesclientesjob.reader;

import br.com.wepdev.enviopromocoesclientesjob.dominio.Cliente;
import br.com.wepdev.enviopromocoesclientesjob.dominio.InteresseProdutoCliente;
import br.com.wepdev.enviopromocoesclientesjob.dominio.Produto;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe leitora do banco de dados
 */
@Configuration
public class LerInteresseProdutoClienteReaderConfig {


    @Bean
    public JdbcCursorItemReader<InteresseProdutoCliente> lerInteresseProdutoClienteReader(@Qualifier("appDataSource")DataSource dataSource){

        /**
         * Na consulta não foi utilizado Alias, apelidos para algumas propriedades e por conta disso ocorrera conflitos por conta do nome de algumas propriedades
         * como o ID e o NOME, que sao propriedades que existem tanto no Cliente como no Produto. Para resolver na hora de setar as propriedades
         * foi colocado o indice
         */
        return new JdbcCursorItemReaderBuilder<InteresseProdutoCliente>()
                .name("lerInteresseProdutoClienteReader")
                .dataSource(dataSource)
                .sql("select * from interesse_produto_cliente " +
                        "join cliente on (cliente = cliente.id)" + 
                        "join produto on (produto = produto.id)")
                .rowMapper(rowMapper())
                .build();
    }

    private RowMapper<InteresseProdutoCliente> rowMapper() {

        return new RowMapper<InteresseProdutoCliente>() {

            @Override
            public InteresseProdutoCliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));

                Produto produto = new Produto();
                produto.setId(rs.getInt(6));
                produto.setNome(rs.getString(7));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));

                InteresseProdutoCliente interesseProdutoCliente = new InteresseProdutoCliente();
                interesseProdutoCliente.setCliente(cliente);
                interesseProdutoCliente.setProduto(produto);

                return interesseProdutoCliente;
            }
        };
    }
}
