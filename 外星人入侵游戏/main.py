import pygame
from settings import Settings
import game_function as gf
from ship import Ship
from pygame.sprite import Group
from game_stats import Gamestat
from button import Button
from score_broad import Score_broad
from ship_life import Ship_life
from level import Level

def run_game():
    pygame.init()
    settings = Settings()
    screen = pygame.display.set_mode((settings.screen_width,settings.screen_height))
    pygame.display.set_caption("外星人入侵")
    ship = Ship(screen)
    bullets = Group()
    aliens = Group()
    game_stats = Gamestat(settings)
    play_button = Button(screen,settings,"Play")
    score_broad = Score_broad(screen,settings)
    ship_life = Ship_life(screen,settings,game_stats)
    level = Level(screen,settings,game_stats)
    gf.create_aliens(screen,settings,aliens)
    while True:
        gf.check_event(screen,settings,ship,bullets,aliens,game_stats,play_button,ship_life)
        if game_stats.game_active:
            ship.update(settings)
            gf.update_bullet(screen,settings,bullets,aliens,game_stats,score_broad)
            gf.update_aliens(screen,settings,ship,bullets,aliens,game_stats,ship_life)
        gf.update_screen(screen,settings,ship,bullets,aliens,game_stats,play_button,score_broad,ship_life,level)
run_game()
