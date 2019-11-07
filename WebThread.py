from threading import Thread
from CvThread import SB_frame
from http import server
from flask import Flask, render_template, Response, request
import cv2

class WebServer(Thread):
    def __init__(self, sb_frame):
        self.sb_frame = sb_frame
        Thread.__init__(self)

    def run(self):
        app = Flask(__name__)

        @app.route('/')
        def index():
            return render_template('index.html')

        def gen(frames):
            while True:
                #oriframe = frames.getFrame()
                #scaledFrame = cv2.resize(oriframe,(300,300))

                _,jpeg = cv2.imencode('.jpg',frames.getFrame())
                frame = jpeg.tobytes()
                yield (b'--frame\r\n'b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')

        @app.route('/video_feed')
        def video_feed():
            return Response(gen(self.sb_frame),mimetype='multipart/x-mixed-replace; boundary=frame')

        @app.route('/setpoint', methods = ['POST'])
        def setpoint():
            data = request.get_json()
            setpointX = data['setpointX']
            setpointY = data['setpointY']
            return 'OK', 200

        @app.route('/pidValues', methods = ['POST', 'GET'])
        def pidValues():
            if request.method == 'POST':
                data = request.get_json()
                Kp = data['Kp']
                Ki = data['Ki']
                Kd = data['Kd']
                DT = data['DT']
                return 'OK', 200
            else:
                return render_template('pidValues.html')

        if __name__ == 'WebThread':
            app.run(debug=False)