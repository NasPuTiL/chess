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
    private static final String INIT_DATA = "/initData";

    private static Game game;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            System.out.println("ex.getStackTrace() = " + ex.getMessage());
            response.setStatus(400);
            response.getWriter().write(ex.getMessage());
        }
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        switch (req.getServletPath()) {
            case POSSIBLE_MOVES:
                getPossibleMoves(req, resp);
                break;
            case REFRESH_DATA:
                refreshData(req, resp);
                break;
            case INIT_DATA:
                initData(req, resp);
                break;
            default:
                break;
        }
    }

    private void initData(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        game = new Game();

        resp.setContentType(Util.RETURN_APPLICATION_MSSG);
        resp.setCharacterEncoding(Util.RETURN_CODING);
        resp.getWriter().write(Util.RETURN_MESSAGE_OK);
        resp.setStatus(Util.RETURN_STATUS_CODE_OK);
    }

    private void refreshData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameters = request.getParameterMap();
        String source = parameters.get("source")[0];
        String target = parameters.get("target")[0];
        String turn = parameters.get("turn")[0];

        String respond;
        game.setTurn(turn);
        respond = game.setFigurePosition(source, target);

        response.setContentType(Util.RETURN_APPLICATION_MSSG);
        response.setCharacterEncoding(Util.RETURN_CODING);
        response.getWriter().write(respond);
        response.setStatus(Util.RETURN_STATUS_CODE_OK);
    }

    private void getPossibleMoves(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameters = request.getParameterMap();
        String square = parameters.get("square")[0];

        List<String> possibleMoves = game.findPossibleMoves(square);
        String respond = new Gson().toJson(possibleMoves);

        response.setContentType(Util.RETURN_APPLICATION_MSSG);
        response.setCharacterEncoding(Util.RETURN_CODING);
        response.getWriter().write(respond);
        response.setStatus(Util.RETURN_STATUS_CODE_OK);
    }

}
