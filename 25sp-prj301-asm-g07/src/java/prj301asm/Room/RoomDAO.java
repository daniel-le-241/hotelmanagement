/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prj301asm.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prj301asm.utils.DBUtils;

/**
 *
 * @author ACER
 */
public class RoomDAO {

    public List<RoomDTO> getRoomsByPage(int page, int pageSize, String typeRoom, String sort) {
        List<RoomDTO> list = new ArrayList<RoomDTO>();
        int offSet = (page - 1) * pageSize;

        String sql = " SELECT * FROM rooms ";
        boolean hasFilter = false;

        if (typeRoom != null && !typeRoom.trim().isEmpty()) {
            sql += " WHERE typeName LIKE ? ";
            hasFilter = true;
        }
        if (sort != null && !sort.trim().isEmpty()) {
            sql += " ORDER BY price " + sort + " ";
        } else {
            sql += " ORDER BY roomID ";
        }

        sql += "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement sttm = con.prepareStatement(sql);
            int index = 1;
            if (hasFilter) {
                sttm.setString(index++, "%" + typeRoom + "%");
            }
            sttm.setInt(index++, offSet);
            sttm.setInt(index++, pageSize);

            ResultSet rs = sttm.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt("roomID");
                    String roomName = rs.getString("roomName");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    String typeName = rs.getString("typeName");
                    RoomDTO room = new RoomDTO(id, roomName, typeName, price, description);
                    list.add(room);
                }
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public boolean addRoom(int roomID, String roomName, String typeName, int price, String description) {
        String sql = "INSERT INTO rooms (roomID, roomName, typeName, price, description) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, roomID);
            stmt.setString(2, roomName);
            stmt.setString(3, typeName);
            stmt.setInt(4, price);
            stmt.setString(5, description);
            if (stmt.executeUpdate() > 0) {
                return true;
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public RoomDTO updateRoom(int roomID, String roomName, String typeName, int price, String description) {
        RoomDTO room = null;
        String sql = "UPDATE rooms SET roomName = ?, typeName = ?, price = ?, description = ? WHERE roomID = ?";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, roomName);
            stmt.setString(2, typeName);
            stmt.setInt(3, price);
            stmt.setString(4, description);
            stmt.setInt(5, roomID);

            if (stmt.executeUpdate() > 0) {
                room = new RoomDTO(roomID, roomName, typeName, price, description);
                return room;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteRoom(String roomID) {
        String sql = "DELETE FROM rooms WHERE roomID = ?";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, roomID);
            if (stmt.executeUpdate() > 0) {
                return true;
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public RoomDTO getRoomByID(int roomID) {
        RoomDTO room = null;

        String sql = " SELECT * FROM rooms WHERE roomID = ? ";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, roomID);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                room = new RoomDTO();
                int id = rs.getInt("roomID");
                String roomName = rs.getString("roomName");
                String typeName = rs.getString("typeName");
                int price = rs.getInt("price");
                String description = rs.getString("description");

                room = new RoomDTO(id, roomName, typeName, price, description);
                return room;
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return room;
    }

    public List<RoomDTO> getAllRoom() {
        List<RoomDTO> getAllRoom = new ArrayList<>();
        String sql = " SELECT * FROM rooms ";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    int roomID = rs.getInt("roomID");
                    String roomName = rs.getString("roomName");
                    String typeName = rs.getString("typeName");
                    int price = rs.getInt("price");
                    String description = rs.getString("description");

                    RoomDTO room = new RoomDTO(roomID, roomName, typeName, price, description);
                    getAllRoom.add(room);
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return getAllRoom;
    }

    public int countRoomsByTypeRoom(String typeRoom) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS totalRooms FROM Rooms";

        if (typeRoom != null && !typeRoom.trim().isEmpty()) {
            sql += " WHERE typeName LIKE ?";
        }

        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement sttm = con.prepareStatement(sql);

            if (typeRoom != null && !typeRoom.isEmpty()) {
                sttm.setString(1, "%" + typeRoom + "%");
            }

            ResultSet rs = sttm.executeQuery();
            if (rs != null && rs.next()) {
                count = rs.getInt("totalRooms");
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("SQLException in countRoomsByTypeRoom: " + e.getMessage());
            e.printStackTrace();
        }
        return count;
    }
}
