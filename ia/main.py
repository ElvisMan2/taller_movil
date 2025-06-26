from fastapi import FastAPI
from pydantic import BaseModel
import joblib
import numpy as np
from fastapi.middleware.cors import CORSMiddleware

# Cargar el modelo entrenado
model = joblib.load("kidney_model.joblib")

# Crear la app
app = FastAPI(title="Kidney Disease Prediction API")

# Configuración CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Permitir todos los orígenes
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

from pydantic import BaseModel
import joblib
import numpy as np

# Cargar el modelo entrenado
model = joblib.load("kidney_model.joblib")

# Crear la app
app = FastAPI(title="Kidney Disease Prediction API")

# Definir los campos esperados
class InputData(BaseModel):
    serumCreatinine: float
    gfr: float
    proteinInUrine: float
    itching: float
    muscleCramps: float
    serumElectrolytesSodium: float
    gender: int
    age: int

@app.get("/")
def root():
    return {"mensaje": "API de predicción de enfermedad renal crónica"}

@app.post("/predict")
def predict(input_data: InputData):
    data = np.array([[v for v in input_data.dict().values()]])
    prediction = model.predict(data)[0]
    return {"riesgo_enfermedad_renal": bool(prediction)}
