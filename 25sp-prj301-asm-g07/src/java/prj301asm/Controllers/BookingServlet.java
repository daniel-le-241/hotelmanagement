/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prj301asm.Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import prj301asm.Room.RoomDAO;
import prj301asm.Room.RoomDTO;
import prj301asm.Booking.BookingDAO;
import prj301asm.Booking.BookingDTO;
import prj301asm.User.UserDTO;

/**
 *
 * @author ACER
 */
public class BookingServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        /* TODO output your page here. You may use following sample code. */
        HttpSession session = request.getSession(false);
        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user.getRole().equals("admin")) {
            BookingDAO dao = new BookingDAO();
            ArrayList<BookingDTO> list = (ArrayList<BookingDTO>) dao.getAllRoomBookings();

            request.setAttribute("roomBookings", list);
            request.getRequestDispatcher("manageBookings.jsp").forward(request, response);
            return;
        } else if (user.getRole().equals("member")) {

            int roomID = Integer.parseInt(request.getParameter("roomID"));
            String action = request.getParameter("action");

            RoomDAO roomDao = new RoomDAO();
            RoomDTO room = roomDao.getRoomByID(roomID);
            if (action.equals("booking")) {
                request.setAttribute("bookingID", generateBookingID());
                request.setAttribute("room", room);

                request.getRequestDispatcher("booking.jsp").forward(request, response);
                return;
            } else if (action.equals("payment")) {

            }
        }

    }

    protected String generateBookingID() {
        String uniqueID = null;
        int nextNumber = 0001;
        BookingDAO dao = new BookingDAO();
        String maxID = dao.getMaxBookingId();
        if (maxID != null) {
            String numPart = maxID.replace("Booking", "");
            try {
                int currentMax = Integer.parseInt(numPart);
                nextNumber = currentMax + 1;
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
        }
        uniqueID = "B" + String.format("%06d", nextNumber);
        return uniqueID;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
