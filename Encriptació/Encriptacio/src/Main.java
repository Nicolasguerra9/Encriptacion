import javax.crypto.Cipher;
import java.security.*;
import java.util.*;

public class Main {

    private static PrivateKey clauPrivada;
    private static PublicKey clauPublica;


    public static void main(String[] args) throws Exception {

        boolean sortir = false, generades = false;
        String encriptar, encriptat, desencriptat, desencriptar;

        do {

            switch (showMenu()) {

                case 0:

                    System.out.println("Has sortit");
                    sortir = true;

                    break;

                case 1:

                    generarClaus();
                    generades = true;

                    break;

                case 2:

                    if (generades){

                        System.out.println("Escriu un missatge");
                        encriptar = Keyboard.readString();

                        encriptat = encriptarMissatge(encriptar);

                        System.out.println(encriptat);

                    } else {

                        System.out.println("No has generat les contrasenyes");

                    }

                    break;

                case 3:

                    if (generades){


                        System.out.println("Escriu un missatge encriptat");
                        desencriptar = Keyboard.readString();
                        desencriptat = desencriptarMissatge(desencriptar);
                        System.out.println(desencriptat);

                    } else {

                        System.out.println("No has generat les contrasenyes");

                    }

                    break;

                default:
                    System.out.println("Opció incorrecte");

            }

        } while (!sortir);

    }

    private static int showMenu() {

        int opcio;

        System.out.println("");
        System.out.println("---------------Menú---------------");
        System.out.println("0. Sortir");
        System.out.println("1. Crear claus");
        System.out.println("2. Encriptar missatge");
        System.out.println("3. Desencriptar missatge");
        System.out.println("");

        opcio = Keyboard.readInt();

        return opcio;

    }

    private static void generarClaus() {

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            KeyPair claus = generator.generateKeyPair();
            clauPrivada = claus.getPrivate();
            clauPublica = claus.getPublic();
        } catch (Exception ignored){}

    }

    private static String encriptarMissatge (String missatge) throws Exception {

        byte[] missatgeToBytes= missatge.getBytes();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE,clauPublica);
        byte[] bytesEncriptats = cipher.doFinal(missatgeToBytes);
        return codificar(bytesEncriptats);
    }

    private  static  String codificar(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private static String desencriptarMissatge (String missatgeEncriptat) throws Exception {


        byte[] missatgeEncriptatBytes = decodificar(missatgeEncriptat);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE,clauPrivada);
        byte[] bytesDesencriptats = cipher.doFinal(missatgeEncriptatBytes);

        return new String(bytesDesencriptats, "UTF8");

    }

    private static byte[] decodificar (String data) {

        return Base64.getDecoder().decode(data);

    }

}
