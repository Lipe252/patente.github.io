import java.security.MessageDigest;
import java.util.HashMap;
import java.util.UUID;
import java.time.LocalDate;
import java.util.Scanner;

public class Core {

    static class HashUtil {
        public static String gerarHash(String senha) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = md.digest(senha.getBytes());

                StringBuilder sb = new StringBuilder();
                for (byte b : hashBytes) {
                    sb.append(String.format("%02x", b));
                }

                return sb.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Usuario {
        String id;
        String nome;
        private String senhaHash;
        int patentes;

        public Usuario(String nome, String senha, int patentes) {
            this.id = UUID.randomUUID().toString();
            this.nome = nome;
            this.senhaHash = HashUtil.gerarHash(senha);
            this.patentes = patentes;
        }

        public boolean verificarSenha(String senhaDigitada) {
            String hashDigitado = HashUtil.gerarHash(senhaDigitada);
            return this.senhaHash.equals(hashDigitado);
        }

        public String getNome() {
            return nome;
        }
    }

    static class UsuarioService {

        private HashMap<String, Usuario> usuarios = new HashMap<>();

        public boolean criarUsuario(String nome, String senha) {
            if (nome == null || nome.trim().isEmpty()) return false;
            if (senha == null || senha.trim().isEmpty()) return false;
            if (buscarPorNome(nome) != null) return false;

            Usuario user = new Usuario(nome, senha, 0);
            usuarios.put(user.id, user);
            return true;
        }

        public Usuario buscarUsuario(String id) {
            return usuarios.get(id);
        }

        public Usuario buscarPorNome(String nome) {
            for (Usuario u : usuarios.values()) {
                if (u.getNome().equals(nome)) return u;
            }
            return null;
        }

        public Usuario login(String nome, String senha) {
            if (nome == null || nome.trim().isEmpty()) return null;
            if (senha == null || senha.trim().isEmpty()) return null;

            Usuario user = buscarPorNome(nome);
            if (user == null) return null;
            if (!user.verificarSenha(senha)) return null;

            return user;
        }

        public boolean addpatente(String id, int valor) {
            Usuario user = usuarios.get(id);
            if (user == null) return false;

            int nova = user.patentes + valor;
            if (nova < 0) return false;

            user.patentes = nova;
            return true;
        }

        public boolean removerUsuario(String id) {
            return usuarios.remove(id) != null;
        }
    }

    static class Patente {
        String id;
        LocalDate dataInc;
        LocalDate dataFin;
        String criador;
        boolean status;
        String descricao;
        String nome;

        private HashMap<String, Patente> patentes = new HashMap<>();

        public Patente(LocalDate dataInc, LocalDate dataFin, String criador, String descricao, String nome) {
            if (dataFin.isBefore(dataInc)) {
                throw new IllegalArgumentException("Data final não pode ser antes da inicial");
            }

            this.id = UUID.randomUUID().toString();
            this.dataInc = dataInc;
            this.dataFin = dataFin;
            this.criador = criador;
            this.descricao = descricao;
            this.nome = nome;
            this.status = LocalDate.now().isBefore(dataFin);
        }

        public boolean cadastrar(String nome, String criador) {
            if (nome == null || nome.trim().isEmpty()) return false;
            if (criador == null || criador.trim().isEmpty()) return false;

            Patente p = new Patente(
                LocalDate.now(),
                LocalDate.now().plusYears(1),
                criador,
                "Sem descrição",
                nome
            );

            patentes.put(p.id, p);
            return true;
        }
    }

    public static void main(String[] args) {

        UsuarioService service = new UsuarioService();
        service.criarUsuario("Vinicius", "123456");

        if (args.length > 0 && args[0].equals("api")) {
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            if (input.contains("login")) {
                String nome = input.split("\"nome\":\"")[1].split("\"")[0];
                String senha = input.split("\"senha\":\"")[1].split("\"")[0];

                Usuario user = service.login(nome, senha);

                if (user != null) {
                    System.out.println("{\"status\":\"ok\",\"nome\":\"" + user.getNome() + "\"}");
                } else {
                    System.out.println("{\"status\":\"erro\"}");
                }
            }

            sc.close();
            return;
        }

        Usuario u = service.login("Vinicius", "123456");
        System.out.println(u != null ? "Login OK: " + u.getNome() : "Falha no login");
    }
}