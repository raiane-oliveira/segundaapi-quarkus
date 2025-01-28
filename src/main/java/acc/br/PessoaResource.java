package acc.br;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import acc.br.model.Pessoa;

@Path("/pessoas")
public class PessoaResource {

  @GET
  public List<Pessoa> listarTodas(@QueryParam("idade") Optional<Integer> idade) {
    if (idade.isPresent()) {
      return findOlderThan(idade.get());
    }

    return Pessoa.listAll();
  }

  @GET
  @Path("/{nome}")
  public Pessoa buscarPorNome(@PathParam("nome") String nome) {
    return Pessoa.findByName(nome);
  }

  @POST
  @Transactional
  public Response criarPessoa(Pessoa pessoa) {
    pessoa.persist();
    return Response.status(Response.Status.CREATED).entity(pessoa).build();
  }

  public List<Pessoa> findOlderThan(int idade) {
    return Pessoa.list("idade > ?1", idade);
  }
}
