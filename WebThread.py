from threading import Thread
from CvThread import SB_frame
from http import server
from flask import Flask, render_template, Response

class WebServer(Thread):
    def __init__(self, sb_frame):
        self.sb_frame = sb_frame
        Thread.__init__(self)

    def run(self):
        app = Flask(__name__)

        @app.route('/')
        def index():
            return 'HALLA'

        if __name__ == '__main__':
            app.run(debug=True)