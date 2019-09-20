class Gamestat():
    def __init__(self,settings):
        self.settings = settings
        self.reset_stat()
        self.game_active = False
        with open("highest.txt","r") as file_obj:
            self.highest_score = int(file_obj.read())

    def reset_stat(self):
        self.ship_life = self.settings.ship_life
        self.level = 1
        self.score = 0

    def refresh_score(self):
        self.highest_score = max(self.highest_score,self.score)