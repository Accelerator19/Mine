import pygame

class Button():
    def __init__(self,screen,settings,message):
        self.screen = screen
        self.screen_rect = self.screen.get_rect()
        self.settings = settings
        self.rect = pygame.Rect(0,0,settings.button_width,settings.button_height)
        self.rect.center = self.screen_rect.center
        self.font = pygame.font.SysFont(None,48)
        self.print_message(message)

    def print_message(self,message):
        self.message = self.font.render(message,True,self.settings.text_color,self.settings.button_color)
        self.message_rect = self.message.get_rect()
        self.message_rect.center = self.rect.center

    def draw_button(self):
        self.screen.fill(self.settings.button_color,self.rect)
        self.screen.blit(self.message,self.message_rect)

