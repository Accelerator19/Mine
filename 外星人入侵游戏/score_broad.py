import pygame

class Score_broad():
    def __init__(self,screen,settings):
        self.settings = settings
        self.screen = screen
        self.screen_rect = self.screen.get_rect()
        self.font = pygame.font.SysFont(None,48)


    def print_score(self,game_stats):
        self.highest_pic = self.font.render("Highest:" + str(game_stats.highest_score),True,self.settings.score_color,self.settings.bg_color)
        self.highest_rect = self.highest_pic.get_rect()
        self.score_pic = self.font.render("score:"+str(game_stats.score),True,self.settings.score_color,self.settings.bg_color)
        self.score_rect = self.score_pic.get_rect()
        self.highest_rect.right = self.screen_rect.right - 20
        self.highest_rect.top = 20
        self.score_rect.right = self.screen_rect.right - 20
        self.score_rect.top = 40 + self.highest_rect.height

    def draw_score(self,game_stats):
        self.print_score(game_stats)
        self.screen.blit(self.score_pic,self.score_rect)
        self.screen.blit(self.highest_pic,self.highest_rect)