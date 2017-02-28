package com.test.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.web.model.GameDetail;
import com.web.service.GameService;

public class TestGameWinner {
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("WEB-INF/spring-config.xml");
		
		GameService gameService = (GameService) context.getBean("gameService");
		GameDetail game = new GameDetail();
		game.setGameId("481b9559f1ec419490447116e35a5068");
		game.setActivePlayer(11);
		game.setPlayer1(11);
		game.setPlayer2(12);
		game.setHandP2("S01,S02,S03,S04,S05,C05,H05,");
		
		System.out.println(gameService.checkWinner(game));
	}
}
