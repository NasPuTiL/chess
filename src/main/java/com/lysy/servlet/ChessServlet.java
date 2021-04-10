package com.lysy.servlet;

import com.google.gson.Gson;
import com.lysy.game.Game;
import com.lysy.util.Util;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ChessServlet extends HttpServlet {
    private static final String POSSIBLE_MOVES = "/getPossibleMoves";
    private static final String REFRESH_DATA = "/refreshData";

    private static Game game;
    static{
        game = new Game();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            response.setStatus(400);
            response.getWriter().write(ex.getMessage());
        }
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, CloneNotSupportedException {
        String path = req.getServletPath();
        switch (path) {
            case POSSIBLE_MOVES:
                getPossibleMoves(req, resp);
                break;
            case REFRESH_DATA:
                refreshData(req, resp);
                break;
            default:
                break;
        }
    }

    private void refreshData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameters = request.getParameterMap();
        String source = parameters.get("source")[0];
        String target = parameters.get("target")[0];
        String turn = parameters.get("turn")[0];

        game.setTurn(turn);
        game.setFigurePosition(source, target);
        String respond = new Gson().toJson(game.getStatus(target));


        response.setContentType(Util.RETURN_APPLICATION_MSSG);
        response.setCharacterEncoding(Util.RETURN_CODING);
        response.getWriter().write(respond);
        response.setStatus(Util.RETURN_STATUS_CODE_OK);
    }

    private void getPossibleMoves(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameters = request.getParameterMap();
        String square = parameters.get("square")[0];
        String piece = parameters.get("piece")[0];
        String turn = parameters.get("turn")[0];

        System.out.println("square = " + square);
        System.out.println("piece = " + piece);
        System.out.println("turn = " + turn);

        List<String> possibleMoves = game.findPossibleMoves(square);
        String respond = new Gson().toJson(possibleMoves);

        response.setContentType(Util.RETURN_APPLICATION_MSSG);
        response.setCharacterEncoding(Util.RETURN_CODING);
        response.getWriter().write(respond);
        response.setStatus(Util.RETURN_STATUS_CODE_OK);
    }

}
