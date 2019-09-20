import pygame

class Level():
    def __init__(self,screen,settings,game_stats):
        self.game_stats = game_stats
        self.settings = settings
        self.screen = screen
        self.screen_rect = self.screen.get_rect()
        self.font = pygame.font.SysFont(None,48)

    def print_level(self):
        self.level_pic = self.font.render("Level "+str(self.game_stats.level),True,self.settings.score_color,self.settings.bg_color)
        self.level_rect = self.level_pic.get_rect()
        self.level_rect.centerx = self.screen_rect.centerx
        self.level_rect.top = 20

    def draw_level(self):
        self.print_level()
        self.screen.blit(self.level_pic,self.level_rect)