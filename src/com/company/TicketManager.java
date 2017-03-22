package com.company;

import java.io.*;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class TicketManager {

    private static LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();
    private LinkedList<Ticket> resolvedTickets = new LinkedList<>();



    private void mainMenu() throws Exception{

        while (true) {

            //TODO problem 4 - add two new options: Delete by Issue and Search by Issue
            System.out.println("1. Enter Ticket\n2. Delete Ticket by ID\n3. Delete Ticket by Issue\n4. Search by Issue\n5. Display All Tickets\n6. Quit");

            int task = Input.getPositiveIntInput("Enter your selection");

            if (task == 1) {
                addTickets();
            }
            else if (task == 2) {
                deleteTicketById();
            }
            else if (task == 3)
            {
                deleteTicketByIssue(ticketQueue);
            }
            else if (task == 4)
            {
                searchByIssue(ticketQueue);
            }
            else if (task == 5) {
                printAllTickets();
            }
            else if ( task == 6) {
                BufferedWriter quitSave = new BufferedWriter(new FileWriter("open_tickets.txt"));
                for (Ticket ticket : ticketQueue)
                {
                    quitSave.write("Issue: " + ticket.getDescription() + "\npriority: " + ticket.getPriority() + "\nreported: " + ticket.getReporter()
                            + "\ndescription: " + ticket.getDescription() + "\ndate reported: " + ticket.getDateReported());

                }

                String date = new SimpleDateFormat("mm_dd_yyyy").format(new Date());
                String filename = "Resolved_tickets_as_of_" + date + ".txt";

                BufferedWriter fileSave = new BufferedWriter(new FileWriter(filename));
                for (Ticket ticket : ticketQueue)
                {
                    quitSave.write("Issue: " + ticket.getDescription() + "\npriority: " + ticket.getPriority() + "\nreported: " + ticket.getReporter()
                            + "\ndescription: " + ticket.getDescription() + "\ndate reported: " + ticket.getDateReported() + "\ndate resolved: "
                            + ticket.getDateResolved() + "\nresolution: " + ticket.getResolution());
                }
                quitSave.close();
                fileSave.close();

                System.out.println("Quitting program");
                // TODO Problem 7 save all open tickets, and today's resolved tickets, to a file
                break;
            }
            else {
                //this will happen for 3 or any other selection that is a valid int
                //Default will be print all tickets
                printAllTickets();
            }
        }
    }


    protected LinkedList<Ticket> searchDescription(String searchString) {
        // TODO problem 3: complete this method - it should return a
        // list of the tickets that contain the searchString in the description.
        // Return an empty list if there are no matching Tickets.
        // The search should be case-insensitive

        return null;  //replace this with a return statement that returns a list
    }


    protected void searchByIssue(LinkedList<Ticket> ticketQueue) {
        // TODO problem 4 implement this method. Return a list of matching tickets.
        LinkedList<Ticket> searchstring = new LinkedList<Ticket>();
        printAllTickets();
        if (ticketQueue.size() == 0)
        {
            System.out.println("No tickets to search for, please try again.");
            return;
        }
        String search = Input.getStringInput("Enter keyword of ticket to issue.");
        for (Ticket tx : ticketQueue)
        {
            if (tx.getDescription().contains(search))
            {
                searchstring.add(tx);
            }
        }
        System.out.println("List of possible tickets." + searchstring);
        if (searchstring.size() == 0)
        {
            System.out.println("No tickets matching that description.");
            searchByIssue(ticketQueue);
        }
        // Ask user for search term
        // Use searchDescription() method to get list of matching Tickets
        // display list
    }


    protected void deleteTicketByIssue(LinkedList<Ticket> ticketQueue) {
        LinkedList<Ticket> deleteissue = new LinkedList<Ticket>();
        printAllTickets();
        if (ticketQueue.size() == 0)
        {
            System.out.println("No tickets to delete, please try again.");
            return;
        }
        String deleteT = Input.getStringInput("Enter keyword for ticket to be deleted.");
        for (Ticket tx : ticketQueue)
        {
            if (tx.getDescription().contains(deleteT))
            {
                deleteissue.add(tx);
            }
        }
        System.out.println("List of possible tickets." + deleteissue);
        if (deleteissue.size() == 0)
        {
            System.out.println("No tickets matching that description.");
            deleteTicketByIssue(ticketQueue);
        }
        else if (deleteissue.size() > 0)
        {
            deleteTicketById();
        }
        // TODO problem 5 implement this method
        // Ask user for string to search for
        // Use searchDescription to create list of matching Tickets
        // Ask for ID of ticket to delete
        // Delete that ticket
    }


    protected void deleteTicketById() {

        printAllTickets();   //display list for user

        if (ticketQueue.size() == 0) {    //no tickets!
            System.out.println("No tickets to delete!\n");
            return;
        }

        int deleteID = Input.getPositiveIntInput("Enter ID of ticket to delete");
        String userResolved = Input.getStringInput("How was this issue resolved?");
        Date today = new Date();

        //Loop over all tickets. Delete the one with this ticket ID
        boolean found = false;
        for (Ticket ticket : ticketQueue) {
            if (ticket.getTicketID() == deleteID) {
                found = true;
                ticket.setDateResolved(today);
                ticket.setResolution(userResolved);
                resolvedTickets.add(ticket);
                ticketQueue.remove(ticket);
                System.out.println(String.format("Ticket %d deleted", deleteID));
                break; //don't need the loop any more.
            }
        }
        if (!found) {
            System.out.println("Ticket ID not found, please enter ticket ID again.");
            deleteTicketById();
            //TODO Problem 2 re-write this method to ask for ID again if not found
        }
        printAllTickets();  //print updated list

    }


    protected void addTickets() {

        while (true) {

            Date dateReported = new Date(); //Default constructor creates Date with current date/time

            String description = Input.getStringInput("Enter problem");
            String reporter = Input.getStringInput("Who reported this issue?");
            int priority = Input.getPositiveIntInput("Enter priority of " + description);

            Ticket t = new Ticket(description, priority, reporter, dateReported, null, null);
            //ticketQueue.add(t);
            addTicketInPriorityOrder(t);

            printAllTickets();

            String more = Input.getStringInput("More tickets to add? Enter N for no, anything else to add more tickets");

            if (more.equalsIgnoreCase("N")) {
                return;
            }
        }
    }


    protected void addTicketInPriorityOrder(Ticket newTicket){

        //Logic: assume the list is either empty or sorted

        if (ticketQueue.size() == 0 ) {//Special case - if list is empty, add ticket and return
            ticketQueue.add(newTicket);
            return;
        }

        //Tickets with the HIGHEST priority number go at the front of the list. (e.g. 5=server on fire)
        //Tickets with the LOWEST value of their priority number (so the lowest priority) go at the end

        int newTicketPriority = newTicket.getPriority();

        for (int x = 0; x < ticketQueue.size() ; x++) {    //use a regular for loop so we know which element we are looking at

            //if newTicket is higher or equal priority than the this element, add it in front of this one, and return
            if (newTicketPriority >= ticketQueue.get(x).getPriority()) {
                ticketQueue.add(x, newTicket);
                return;
            }
        }

        //Will only get here if the ticket is not added in the loop
        //If that happens, it must be lower priority than all other tickets. So, add to the end.
        ticketQueue.addLast(newTicket);
    }


    protected void printAllTickets() {
        System.out.println(" ------- All open tickets ----------");

        for (Ticket t : ticketQueue ) {
            System.out.println(t); // This calls the  toString method for the Ticket object.
        }
        System.out.println(" ------- End of ticket list ----------");

    }


    /* Main is hiding down here. Create a TicketManager object, and call the mainMenu method.
    Avoids having to make all of the methods in this class static. */

    public static void main(String[] args) throws Exception{
        TicketManager manager = new TicketManager();

        BufferedWriter fileOpen = new BufferedWriter(new FileWriter("open_tickets.txt", true));
        Scanner inputFile = new Scanner(new File("open_tickets.txt"));

        String dateFormat = "EEE MMM dd hh:mm:ss z yyyy";
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);

        while (inputFile.hasNext())
        {
            Ticket open = new Ticket(inputFile.nextLine(), Integer.parseInt(inputFile.nextLine()),
                    inputFile.nextLine(), format.parse(inputFile.nextLine()), null, null);

            ticketQueue.add(open);
        }
        fileOpen.close();

        //TODO problem 8 load open tickets from a file

        //TODO Problem 9 how will you know what ticket ID to start with?

        manager.mainMenu();
    }

}

