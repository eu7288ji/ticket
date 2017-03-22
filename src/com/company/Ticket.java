package com.company;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;

public class Ticket {

    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private Date dateReported;
    private Date dateResolved;
    private String resolution;

    //TODO Problem 1: explain the role of ticketIdCounter and ticketID

    //STATIC Counter - one variable, shared by all Ticket objects.
    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Make it private - only Ticket objects should have access
    private static int ticketIdCounter = 1;

    //The ID for each ticket - an instance variable. Each Ticket will have it's own ticketID variable
    protected int ticketID;


    protected static void ReadID(){

        try{
            ticketIdCounter = ReadIDCounter();

        }
        catch(Exception ex){
            System.out.println("Error");
        }
    }

    protected static void WriteID(){

        try{
            WriteIDCounter();

        }
        catch(Exception ex){
            System.out.println("Error");
        }
    }

    protected static int ReadIDCounter() throws Exception{

        int countNumber = 1;
        FileReader input = new FileReader("current_tickets.txt");
        countNumber = input.read();
        input.close();
        return countNumber;
    }

    protected static void WriteIDCounter() throws Exception{

        FileWriter output = new FileWriter("current_tickets.txt");
        output.write(ticketIdCounter);
        output.close();
    }

    // TODO problem 6: tickets need to store the resolution date and a string describing the resolution
    // Either add them to this class or create another class called ResolvedTicket - which
    // do you think is the better approach?

    public Ticket(String desc, int p, String rep, Date date, Date resolveDate, String resolute) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.dateResolved = resolveDate;
        this.resolution = resolute;
        this.ticketID = ticketIdCounter;
        ticketIdCounter++;
    }

    protected int getPriority() {
        return priority;
    }

    public int getTicketID() {
        return ticketID;
    }

    public String getDescription() {
        return description;
    }

    public String getReporter() {
        return reporter;
    }

    public Date getDateReported() {
        return dateReported;
    }

    public Date getDateResolved() {
        return dateResolved;
    }

    public String getResolution() {
        return resolution;
    }

    public static int getTicketIdCounter() {
        return ticketIdCounter;
    }

    public void setDateResolved(Date dateResolved) {
        this.dateResolved = dateResolved;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String toString(){
        return("ID: " + this.ticketID + " Issue: " + this.description + " Priority: " +	this.priority + " Reported by: "
                + this.reporter + " Reported on: " + this.dateReported + " Resolved on: " + this.dateResolved
                + " Description of fix: " + this.resolution);
    }

}

