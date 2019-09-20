class Settings():
    def __init__(self):
        self.screen_width = 800
        self.screen_height = 500
        self.bg_color = (230,230,230)
        self.ship_speed = 1
        self.bullet_width = 3000
        self.bullet_height = 15
        self.bullet_color = (60,60,60)
        self.bullet_speed = 1
        self.level_init()
        self.alien_direction = 1
        self.powerful_bullet = False
        self.ship_life = 3
        self.button_width = 200
        self.button_height = 50
        self.button_color = (0,255,0)
        self.text_color = (255,255,255)
        self.score_color = (30,30,30)

    def level_up(self):
        self.alien_speed+=0.2
        self.alien_drop_speed+=1
        self.allowed_bullet_number+=1
        self.alien_score+=10
        #self.ship_life+=0.2

    def level_init(self):
        self.alien_speed = 1
        self.alien_drop_speed = 10
        self.allowed_bullet_number = 3
        self.alien_score = 50
