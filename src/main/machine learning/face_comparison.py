import cv2
import face_recognition
from fastapi import FastAPI
from pydantic import BaseModel
from urllib.request import urlopen
import uvicorn


# Define the input data model
class FaceComparisonRequest(BaseModel):
    face1: str
    face2: str

# Initialize the FastAPI app
app = FastAPI()

# Define the face comparison endpoint
@app.post("/face-comparison")
def face_comparison(request: FaceComparisonRequest):

    face1 = face_recognition.load_image_file(urlopen(request.face1))
    face2 = face_recognition.load_image_file(urlopen(request.face2))

    # Convert the images to RGB format
    image1 = cv2.cvtColor(face1, cv2.COLOR_BGR2RGB)
    image2 = cv2.cvtColor(face2, cv2.COLOR_BGR2RGB)

    # Detect the faces in the images
    face_locations1 = face_recognition.face_locations(image1)
    face_locations2 = face_recognition.face_locations(image2)

    # Check if there is more than one face detected in either image
    if len(face_locations1) != 1 or len(face_locations2) != 1:
        return {"result": False, "message": "Error: More than one face detected in one or both images"}

    # If there is only one face detected in each image, compare the faces
    else:
        face_encoding1 = face_recognition.face_encodings(image1, face_locations1)[0]
        face_encoding2 = face_recognition.face_encodings(image2, face_locations2)[0]
        distance = face_recognition.face_distance([face_encoding1], face_encoding2)

        # Calculate the accuracy of the face comparison
        # The lower the distance, the more similar the faces are
        # A distance of 0.5 is a good threshold for identifying a match
        accuracy = 1 - distance[0]

        if accuracy >= 0.5:
            return True
        else:
            return False


uvicorn.run(app, host="localhost", port=4040)
