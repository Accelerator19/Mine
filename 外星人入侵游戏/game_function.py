import sys
import pygame
from alien import Alien
from ship import Ship
from bullet import Bullet
from time import sleep

def check_event(screen,settings,ship,bullets,aliens,game_stats,play_button,ship_life):
    for event in pygame.event.get():
        if event.type==pygame.QUIT:
            save_and_exit(game_stats)
        elif event.type==pygame.KEYDOWN:
            if event.key==pygame.K_RIGHT:
                ship.move_right = True
            elif event.key==pygame.K_LEFT:
                ship.move_left = True
            elif event.key==pygame.K_q:
                save_and_exit(game_stats)
            elif event.key==pygame.K_SPACE:
                fire_bullet(screen,settings,ship,bullets)
        elif event.type==pygame.MOUSEBUTTONDOWN:
            mousex,mousey = pygame.mouse.get_pos()
            check_play_button(screen,settings,ship,bullets,aliens,game_stats,play_button,mousex,mousey,ship_life)

        elif event.type==pygame.KEYUP:
            if event.key==pygame.K_RIGHT:
                ship.move_right = False
            elif event.key == pygame.K_LEFT:
                ship.move_left = False

def fire_bullet(screen,settings,ship,bullets):
    if len(bullets) < settings.allowed_bullet_number:
        new_bullet = Bullet(screen,settings,ship)
        bullets.add(new_bullet)

def update_screen(screen,settings,ship,bullets,aliens,game_stats,play_button,score_broad,ship_life,level):
    screen.fill(settings.bg_color)
    for bullet in bullets:
        bullet.draw_bullet()
    ship.blitme()
    aliens.draw(screen)
    score_broad.draw_score(game_stats)
    ship_life.draw_life()
    level.draw_level()
    if not game_stats.game_active:
        play_button.draw_button()
    pygame.display.flip()

def update_bullet(screen,settings,bullets,aliens,game_stats,score_broad):
    bullets.update()
    for bullet in bullets.copy():
        if bullet.rect.bottom < 0:
            bullets.remove(bullet)
    collsions = pygame.sprite.groupcollide(bullets,aliens,not settings.powerful_bullet,True)
    if collsions:
        for aliens in collsions.values():
            game_stats.score+=settings.alien_score * len(aliens)
            score_broad.draw_score(game_stats)
    if len(aliens)==0:
        game_stats.level+=1
        bullets.empty()
        create_aliens(screen,settings,aliens)
        settings.level_up()

def number_alien(settings,alien_width):
    available_space_x = settings.screen_width - 2 * alien_width
    number_alien_x = int(available_space_x / (2 * alien_width))
    return number_alien_x

def number_row(settings,alien_height,ship_height):
    available_space_y = settings.screen_height - 3 * alien_height - ship_height - 100
    row_number = int(available_space_y / (2 * alien_height))
    return row_number

def create_alien(screen,settings,alien_number,row_number,aliens):
    alien = Alien(settings,screen)
    alien.x = alien.rect.width + 2 * alien.rect.width * alien_number
    alien.y = alien.rect.height + 2 * alien.rect.height * row_number
    alien.rect.x = alien.x
    alien.rect.y = alien.y+80
    aliens.add(alien)

def create_aliens(screen,settings,aliens):
    alien = Alien(settings, screen)
    ship = Ship(screen)
    for row_number in range(number_row(settings,alien.rect.height,ship.rect.height)):
        for alien_number in range(number_alien(settings,alien.rect.width)):
            create_alien(settings,screen,alien_number,row_number,aliens)

def change_direction(settings,aliens):
        for alien in aliens:
            if alien.check_edge():
                for alien in aliens:
                    alien.rect.y+=settings.alien_drop_speed
                settings.alien_direction*=-1
                break

def check_alien_bottom(screen,settings,ship,bullets,aliens,game_stats,ship_life):
    screen_rect = screen.get_rect()
    for alien in aliens:
        if alien.rect.bottom>=screen_rect.bottom:
            ship_hit(screen,settings,ship,bullets,aliens,game_stats,ship_life)
            break

def update_aliens(screen,settings,ship,bullets,aliens,game_stats,ship_life):
    change_direction(settings,aliens)
    aliens.update()
    check_alien_bottom(screen,settings,ship,bullets,aliens,game_stats,ship_life)
    if pygame.sprite.spritecollideany(ship,aliens):
        ship_hit(screen,settings,ship,bullets,aliens,game_stats,ship_life)

def ship_hit(screen,settings,ship,bullets,aliens,game_stats,ship_life):
    aliens.empty()
    bullets.empty()
    create_aliens(screen,settings,aliens)
    ship.center_ship()
    sleep(0.5)
    ship_life.draw_life()
    game_stats.ship_life-=1
    if game_stats.ship_life<=0:
        game_stats.reset_stat()
        settings.level_init()
        game_stats.refresh_score()
        game_stats.game_active = False
        pygame.mouse.set_visible(True)

def check_play_button(screen,settings,ship,bullets,aliens,game_stats,play_button,mousex,mousey,ship_life):
    if play_button.rect.collidepoint(mousex,mousey) and not game_stats.game_active:
        pygame.mouse.set_visible(False)
        ship_hit(screen,settings,ship,bullets,aliens,game_stats,ship_life)
        game_stats.reset_stat()
        game_stats.game_active = True

def save_and_exit(game_stats):
    with open("highest.txt","w") as file_obj:
        file_obj.write(str(game_stats.highest_score))
    sys.exit()