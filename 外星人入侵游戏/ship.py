import pygame

class Ship():
    def __init__(self,screen):
        self.screen = screen
        self.image = pygame.image.load("images/ship.bmp")
        self.rect = self.image.get_rect()
        self.screen_rect = self.screen.get_rect()
        self.center_ship()
        self.move_right = False
        self.move_left = False

    def update(self,settings):
        if self.move_right and self.rect.right+settings.ship_speed<self.screen_rect.right:
            self.rect.centerx+=settings.ship_speed
        if self.move_left and self.rect.left-settings.ship_speed>self.screen_rect.left:
            self.rect.centerx-=settings.ship_speed

    def blitme(self):
        self.screen.blit(self.image,self.rect)

    def center_ship(self):
        self.rect.centerx = self.screen_rect.centerx
        self.rect.bottom = self.screen_rect.bottom