import pygame

class Ship_life():
    def __init__(self,screen,settings,game_stats):
        self.game_stats = game_stats
        self.settings = settings
        self.screen = screen
        self.screen_rect = self.screen.get_rect()
        self.font = pygame.font.SysFont(None,48)

    def print_life(self):
        self.life_pic = self.font.render("Life:"+str(self.game_stats.ship_life),True,self.settings.score_color,self.settings.bg_color)
        self.life_rect = self.life_pic.get_rect()
        self.life_rect.left = self.screen_rect.left+20
        self.life_rect.top = 20

    def draw_life(self):
        self.print_life()
        self.screen.blit(self.life_pic,self.life_rect)