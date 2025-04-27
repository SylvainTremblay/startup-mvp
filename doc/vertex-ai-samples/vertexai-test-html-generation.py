import vertexai
from vertexai.language_models import CodeGenerationModel

PROJECT_ID = "hip-apricot-425819-v2"  # @param {type:"string"}
LOCATION = "us-central1"  # @param {type:"string"}
MODEL_NAME = "code-bison@001"  # @param {type:"string"}

vertexai.init(project=PROJECT_ID, location=LOCATION)

parameters = {
    "temperature": 0.5,  # Temperature controls the degree of randomness in token selection.
    "max_output_tokens": 256,  # Token limit determines the maximum amount of text output.
}

code_generation_model = CodeGenerationModel.from_pretrained("code-bison@001")
response = code_generation_model.predict(
    prefix="You are a HTML Developer. Write a html page with a button in the center.", **parameters
)

print(response.text)

