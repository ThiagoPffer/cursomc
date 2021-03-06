package com.thiagobetha.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thiagobetha.cursomc.domain.Categoria;
import com.thiagobetha.cursomc.domain.Cidade;
import com.thiagobetha.cursomc.domain.Cliente;
import com.thiagobetha.cursomc.domain.Endereco;
import com.thiagobetha.cursomc.domain.Estado;
import com.thiagobetha.cursomc.domain.ItemPedido;
import com.thiagobetha.cursomc.domain.Pagamento;
import com.thiagobetha.cursomc.domain.PagamentoComBoleto;
import com.thiagobetha.cursomc.domain.PagamentoComCartao;
import com.thiagobetha.cursomc.domain.Pedido;
import com.thiagobetha.cursomc.domain.Produto;
import com.thiagobetha.cursomc.domain.enums.EstadoPagamento;
import com.thiagobetha.cursomc.domain.enums.TipoCliente;
import com.thiagobetha.cursomc.repositories.CategoriaRepository;
import com.thiagobetha.cursomc.repositories.CidadeRepository;
import com.thiagobetha.cursomc.repositories.ClienteRepository;
import com.thiagobetha.cursomc.repositories.EnderecoRepository;
import com.thiagobetha.cursomc.repositories.EstadoRepository;
import com.thiagobetha.cursomc.repositories.ItemPedidoRepository;
import com.thiagobetha.cursomc.repositories.PagamentoRepository;
import com.thiagobetha.cursomc.repositories.PedidoRepository;
import com.thiagobetha.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Inform??tica");
		Categoria cat2 = new Categoria(null, "Escrit??rio");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
				
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
	
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "S??o Paulo");
		
		Cidade c1 = new Cidade(null, "Uberl??ndia", est1);
		Cidade c2 = new Cidade(null, "S??o Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12332313313", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("912495949", "932405389"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220202", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "424", "Apto 101", "Urussang", "80880122", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("25/05/2021 11:42"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("22/05/2021 10:12"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2021 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, p1.getPreco());
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, p3.getPreco());
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, p2.getPreco());
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
