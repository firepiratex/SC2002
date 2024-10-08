import java.util.Scanner;

public class Patient {
    
    Scanner sc = new Scanner(System.in);


    private PatientDetails[] person; //way to declare array if the array is for another class 


    public Patient() {
        this.person = new PatientDetails[100]; 
        for(int i = 0; i<100; i++){
            this.person[i] = new PatientDetails(i + 1);
        }
    }

    void getMedicalRecord(int patientID){
        System.out.println(person[patientID].getBloodType() + " " + person[patientID].getPDAT());
    }

    void ViewAppSlots(){
    
    }

    void ScheduleApp(){ //reschedule appointment 
    
    }

    void CancelApp(){
    
    }

    void ViewScheduledApp(){
    
    }
    
    void PastAppSlots(){
    
    }

    void logout(){
    
    }
/* 
    public PlaneSeat[] sortSeats() {
        PlaneSeat[] occupiedSeats = new PlaneSeat[12 - numEmptySeat];
        int index = 0;

        for (PlaneSeat seat : this.seat) {
            if (seat.isOccupied()) {
                occupiedSeats[index] = seat;
                index++;
            }
        }

        //.length tells u how many elements are in the array
        for (int i = 0; i < occupiedSeats.length - 1; i++) {
            for (int j = 0; j < occupiedSeats.length - 1 - i; j++) {
                if (occupiedSeats[j].getCustomerID() > occupiedSeats[j + 1].getCustomerID()) {
                    //swap occupiedSeats[j] and occupiedSeats[j + 1]
                    PlaneSeat temp = occupiedSeats[j];
                    occupiedSeats[j] = occupiedSeats[j + 1];
                    occupiedSeats[j + 1] = temp;
                }
            }
        }
        return occupiedSeats;

    }
    

    public void showNumEmptySeats(){
        System.out.println("There are "+numEmptySeat+" empty seats");
    }

    public void showEmptySeats(){
        int i = 0;

        System.out.println("The following seats are empty: ");

        while(i < 12){
            if (!seat[i].isOccupied()){
                System.out.println("SeatID "+seat[i].getSeatID());
            }
            i++;
        }
    }

    public void showAssignedSeats(boolean SeatId){
        int i = 0;
        System.out.println("The seat assignments are as follows:");

        PlaneSeat[] sortedOccupiedSeats = sortSeats(); // Sort by customerID

        if(SeatId){
            while(i<12){
                if(seat[i].isOccupied()){
                    System.out.println("SeatID "+seat[i].getSeatID()+" assigned to CustomerID "+ seat[i].getCustomerID());
                }
                i++;
            }
        }else{
            for (PlaneSeat seat : sortedOccupiedSeats) {
                System.out.println("SeatID " + seat.getSeatID() + " assigned to CustomerID " + seat.getCustomerID());
            }

        }
            /*while(i<12){
                if(seat[i].isOccupied()){
                    System.out.println("CustomerID "+seat[i].getCustomerID()+" assigned to SeatID "+ seat[i].getSeatID());
                }
                i++;
            }
        }*/
        
    }
/*
    public void assignSeat(int SeatID, int customerID){
        if(SeatID < 1 || SeatID > 12 ){
            System.out.println("Invalid Seat ID. Please enter a valid seat number.");
            return;
        }
        PlaneSeat seatToAssign = seat[SeatID -1];

        if(seatToAssign.isOccupied()){
            System.out.println("Seat already assigned to a customer.");
        }else{
            seatToAssign.assign(customerID);
            numEmptySeat --;
            System.out.println("Seat Assigned!");
        }
    }

    public void unAssignSeat(int SeatID){
        
        if (SeatID < 1 || SeatID > 12) {
            System.out.println("Invalid Seat ID. Please enter a valid seat number.");
            return;
        }

        PlaneSeat seatToUnassign = seat[SeatID -1];
        /*System.out.println("Attempting to unassign SeatID " + seatToUnassign.getSeatID() + 
                           ", currently assigned to CustomerID " + seatToUnassign.getCustomerID() + 
                           ", assigned status: " + seatToUnassign.isOccupied());*//* 
        if(seatToUnassign.isOccupied()){
            seatToUnassign.unAssign();

            System.out.println("Seat Unassigned!");
            numEmptySeat++;
            return;
        }else{
            System.out.println("no match");
        }
    }
    
    
   */ 
