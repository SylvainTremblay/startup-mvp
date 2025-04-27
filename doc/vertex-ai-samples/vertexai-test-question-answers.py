import vertexai
import pandas as pd
from vertexai.language_models import TextGenerationModel

PROJECT_ID = "hip-apricot-425819-v2"  # @param {type:"string"}
LOCATION = "us-central1"  # @param {type:"string"}
MODEL_NAME = "text-bison@001"  # @param {type:"string"}

vertexai.init(project=PROJECT_ID, location=LOCATION)

generation_model = TextGenerationModel.from_pretrained("text-bison@001")

prompt = """Q: Who was President of the United States in 1955? Which party did he belong to?\n
            A:
         """
print(
    generation_model.predict(
        prompt,
        max_output_tokens=256,
        temperature=0.1,
    ).text
)
