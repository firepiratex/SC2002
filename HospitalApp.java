import java.util.Scanner;

public class HospitalApp {
   public HospitalApp() {
   }

   public static void main(String[] var0) {
      Scanner sc = new Scanner(System.in);
      Patient var2 = new Patient();
      
      while(true) {
         System.out.println("(1) Show medical records");
         System.out.println("(2) TBC");
         System.out.println("(3) TBC");
         System.out.println("(4) TBC");
         System.out.println("(5) TBC");
         System.out.println("(6) TBC");
         System.out.println("(7) Exit");
         int var3 = sc.nextInt();
         switch (var3) {
            case 1:
                int patientID = sc.nextInt();
                var2.getMedicalRecord(patientID);
                break;
            case 2:

               break;
            case 3:

               break;
            case 4:

               break;
            case 5:

               break;
            case 6:

               break;
            case 7:
               return;
            default:
               System.out.println("Invalid selection.");
         }
      }
   }
}
