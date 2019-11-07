from CvThread import CvCom 
from CvThread import SB_frame
from ServoThread import ServoCom
from WebThread import WebServer

sbFrame = SB_frame()

cvCom = CvCom(sbFrame)
servoCom = ServoCom()
webServer = WebServer(sbFrame)

cvCom.start()
servoCom.start()
webServer.start()