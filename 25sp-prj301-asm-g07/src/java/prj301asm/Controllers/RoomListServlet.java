/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prj301asm.Controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static jdk.nashorn.internal.objects.NativeJava.typeName;
import prj301asm.Room.RoomDAO;
import prj301asm.Room.RoomDTO;
import prj301asm.User.UserDTO;

/**
 *
 * @author hoade
 */
public class RoomListServlet extends HttpServlet {

    private static final int PAGE_SIZE = 9;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user.getRole().equals("member")) {
            String typeRoom = request.getParameter("typeRoom");
            if (typeRoom == null) {
                typeRoom = null;
            }
            String pageParam = request.getParameter("page");
            int page;
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                page = Integer.parseInt(pageParam);
            } else {
                page = 1;
            }

            RoomDAO dao = new RoomDAO();
            ArrayList<RoomDTO> list = (ArrayList<RoomDTO>) dao.getRoomsByPage(page, PAGE_SIZE, typeRoom);

            int totalPages = (int) Math.ceil((double) 50 / PAGE_SIZE);

            request.setAttribute("list", list);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("search", typeRoom);

            request.getRequestDispatcher("roomList.jsp").forward(request, response);
        }else if(user.getRole().equals("admin")){                    
            RoomDAO dao = new RoomDAO();
            ArrayList<RoomDTO> list = (ArrayList<RoomDTO>) dao.getAllRoom();
            request.setAttribute("list", list);                                  
             request.getRequestDispatcher("manageRooms.jsp").forward(request, response);
             
        }
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
