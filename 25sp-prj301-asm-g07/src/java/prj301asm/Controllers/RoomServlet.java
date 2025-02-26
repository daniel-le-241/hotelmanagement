/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prj301asm.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import prj301asm.Room.RoomDAO;
import prj301asm.Room.RoomDTO;
import prj301asm.User.UserDTO;

/**
 *
 * @author hoade
 */
public class RoomServlet extends HttpServlet {

    private static final int PAGE_SIZE = 6;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserDTO user = (UserDTO) session.getAttribute("user");
        String action = request.getParameter("action");

        if (user == null || user.getRole().equals("member")) {
            if (action == null || action.equals("list")) {

                String typeRoom = request.getParameter("typeRoom");
                String sort = request.getParameter("sort");
                if (typeRoom == null) {
                    typeRoom = null;
                }
                if (sort == null) {
                    sort = null;
                }

                int page = Integer.parseInt(request.getParameter("page"));

                RoomDAO dao = new RoomDAO();
                ArrayList<RoomDTO> list = (ArrayList<RoomDTO>) dao.getRoomsByPage(page, PAGE_SIZE, typeRoom, sort);

                int totalRoom = dao.countRoomsByPage(typeRoom);

                int totalPages = (int) Math.ceil((double) totalRoom / PAGE_SIZE);

                request.setAttribute("list", list);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("search", typeRoom);
                request.setAttribute("sort", sort);

                request.getRequestDispatcher("roomList.jsp").forward(request, response);
            } else if (action.equals("details")) {
                String roomIDParam = request.getParameter("roomID");
                if (roomIDParam == null || roomIDParam.trim().isEmpty()) {
                    response.sendRedirect("./roomList?page=1&action=list");
                    return;
                }
                int id = Integer.parseInt(roomIDParam);
                request.setAttribute("id", id);
                RoomDAO dao = new RoomDAO();
                RoomDTO room = dao.getRoomByID(id);
                if (room == null) {
                    response.sendRedirect("./roomList?page=1&action=list");
                } else {
                    request.setAttribute("room", room);
                    forwardRoom(request, response, "roomDetails.jsp");
                }
            }
        } else if (user.getRole().equals("admin")) {
            if (action == null || action.equals("manageList")) {
                RoomDAO dao = new RoomDAO();
                ArrayList<RoomDTO> list = (ArrayList<RoomDTO>) dao.getAllRoom();
                request.setAttribute("list", list);
                forwardRoom(request, response, "manageRooms.jsp");
            } else if (action.equals("details")) {
                Integer roomID = null;
                try {
                    roomID = Integer.parseInt(request.getParameter("roomID"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }
                RoomDAO dao = new RoomDAO();
                RoomDTO room = dao.getRoomByID(roomID);

                request.setAttribute("room", room);
                forwardRoom(request, response, "roomDetails.jsp");

            } else if (action.equals("edit")) {
                Integer roomID = null;
                try {
                    roomID = Integer.parseInt(request.getParameter("roomID"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }
                RoomDAO dao = new RoomDAO();
                RoomDTO room = dao.getRoomByID(roomID);
                request.setAttribute("room", room);
                forwardRoom(request, response, "roomEdit.jsp");

            } else if (action.equals("update")) {
                int roomID = Integer.parseInt(request.getParameter("roomID"));
                String roomName = request.getParameter("roomName");
                String typeName = request.getParameter("typeName");
                int price = Integer.parseInt(request.getParameter("price"));
                String description = request.getParameter("description");
                RoomDAO dao = new RoomDAO();
                RoomDTO room = dao.getRoomByID(roomID);
                room.setRoomName(roomName);
                room.setTypeName(typeName);
                room.setPrice(price);
                room.setDescription(description);
                room = dao.updateRoom(roomID, roomName, typeName, price, description);
                if (room == null) {
                    request.setAttribute("error", "Update failed. Try again");
                    forwardRoom(request, response, "roomEdit.jsp");
                } else {
                    response.sendRedirect("./manageRooms");
                }

            }
        }
    }

    private void forwardRoom(HttpServletRequest request, HttpServletResponse response, String page)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }

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
