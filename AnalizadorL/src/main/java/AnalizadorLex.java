import java.io.*;
import java.util.regex.*;

public class AnalizadorLex {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("fuente.txt"));
            PrintWriter writer = new PrintWriter(new FileWriter("output.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = lexJson(line);
                for (String token : tokens) {
                    writer.print(token + " ");
                }
                writer.println();  // Nueva línea para cada línea del archivo fuente
            }

            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] lexJson(String input) {
        String pattern = "\\{|\\}|\\[|\\]|:|,|true|TRUE|false|FALSE|null|NULL|\"[^\"]*\"|-?\\d+";

        // Crear un patrón de expresión regular
        Pattern regex = Pattern.compile(pattern);

        // Usar el patrón para buscar coincidencias en la entrada
        Matcher matcher = regex.matcher(input);

        // Listado de Matcheos por TOKEN
        StringBuilder tokenList = new StringBuilder();
        while (matcher.find()) {
            String token = matcher.group();
            switch (token) {
                case "{":
                    tokenList.append("L_LLAVE");
                    break;
                case "}":
                    tokenList.append("R_LLAVE");
                    break;
                case "[":
                    tokenList.append("L_CORCHETE");
                    break;
                case "]":
                    tokenList.append("R_CORCHETE");
                    break;
                case ":":
                    tokenList.append("DOS_PUNTOS");
                    break;
                case ",":
                    tokenList.append("COMA");
                    break;
                case "true":
                    tokenList.append("PR_" + token.toUpperCase());
                    break;
                case "TRUE":
                    tokenList.append("PR_" + token);
                    break;
                case "false":
                    tokenList.append("PR_" + token.toUpperCase());
                    break;
                case "FALSE":
                    tokenList.append("PR_" + token);
                    break;
                case "null":
                    tokenList.append("PR_"+token.toUpperCase());
                    break;
                case "NULL":
                    tokenList.append("PR_NULL");
                    break;
                default:
                    if (token.matches("\"[^\"]*\"")) {
                        tokenList.append("LITERAL_CADENA");
                    } else if (token.matches("-?\\d+")) {
                        tokenList.append("LITERAL_NUM");
                    } else {
                        tokenList.append("Error léxico: Caracter no reconocido");
                    }
            }
            tokenList.append(" ");
        }

        return tokenList.toString().trim().split(" ");
    }
}
