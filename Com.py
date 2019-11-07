from CvThread import CvCom 
from CvThread import SB_frame
from ServoThread import ServoCom
#from WebThread import WebServer

sbFrame = SB_frame()

cvCom = CvCom(sbFrame)
servoCom = ServoCom()
#webServer = WebServer(sbFrame)

#cvCom.daemon = True
#servoCom.daemon = True
#webServer.daemon = True

cvCom.start()
servoCom.start()
#webServer.start()

while True:
    pass