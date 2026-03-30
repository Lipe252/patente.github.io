import java.security.MessageDigest;
import java.util.HashMap;
import java.util.UUID;

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
            if (nome == null || nome.trim().isEmpty()) {
                System.err.println("Usuario invalido");
                return false;
            }

            if (senha == null || senha.trim().isEmpty()) {
                System.err.println("Senha invalida");
                return false;
            }

            if (buscarPorNome(nome) != null) {
                System.err.println("Usuario existente");
                return false;
            }

            Usuario user = new Usuario(nome, senha, 0);
            usuarios.put(user.id, user);

            return true;
        }

        public Usuario buscarUsuario(String id) {
            return usuarios.get(id);
        }

        public Usuario buscarPorNome(String nome) {
            for (Usuario u : usuarios.values()) {
                if (u.getNome().equals(nome)) {
                    return u;
                }
            }
            return null;
        }

        public boolean login(String nome, String senha) {
            if (nome == null || nome.trim().isEmpty()) {
                System.err.println("Nome invalido");
                return false;
            }

            if (senha == null || senha.trim().isEmpty()) {
                System.err.println("Senha invalida");
                return false;
            }

            Usuario user = buscarPorNome(nome);

            if (user == null) {
                System.out.println("Usuario nao existe");
                return false;
            }

            if (!user.verificarSenha(senha)) {
                System.err.println("Senha incorreta");
                return false;
            }

            return true;
        }
    }

    public static void main(String[] args) {
        UsuarioService service = new UsuarioService();

        service.criarUsuario("Vinicius", "123456");
        service.criarUsuario("Maria", "abc123");

        System.out.println(service.login("Vinicius", "123456"));
        System.out.println(service.login("Vinicius", "errada"));
        System.out.println(service.login("Joao", "123"));
    }
}