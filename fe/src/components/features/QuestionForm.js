import styled from "styled-components";
import { useState } from "react";
import { useNavigate } from "react-router";
import axios from "axios";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

function QuestionForm() {
  const [titleValue, setTitleValue] = useState("");
  const [bodyValue, setBodyValue] = useState("");

  const [isTitleValid, setIsTitleValid] = useState(true);
  const [isBodyValid, setIsBodyValid] = useState(true);

  const navigate = useNavigate();

  const handleTitleChange = (e) => {
    setTitleValue(e.target.value);
  };

  const handleBodyChange = (e) => {
    setBodyValue(e);
  };

  const handleTitleBlur = () => {
    if (titleValue.trim().length < 15) {
      setIsTitleValid(false);
    } else {
      setIsTitleValid(true);
    }
  };

  const handleBodyBlur = () => {
    validateBodyContent(bodyValue);
  };

  const validateBodyContent = (content) => {
    const textOnly = content.replace(/<[^>]+>/g, "");

    if (textOnly.length < 220) {
      setIsBodyValid(false);
    } else {
      setIsBodyValid(true);
    }
  };

  const handleSubmit = async () => {
    if (titleValue.trim().length === 0 || !isTitleValid || !isBodyValid) {
      alert("Please ensure that the title and body meet the requirements.");
      return;
    }
    console.log("Post요청", "title:", titleValue, "body:", bodyValue);
    try {
      const response = await axios.post("http://localhost:8080/v1/questions", {
        title: titleValue,
        body: bodyValue,
      });
      if (response.status >= 200 && response.status < 300) {
        alert("Question successfully posted!");
        navigate("/");
      } else {
        alert("Error posting question: server error");
        console.error("Server responded with an error:", response.data.message || "Unknown server error");
      }
    } catch (error) {
      alert("Error while trying to post question:", error);
      console.error("Error while trying to post question:", error);
    }
  };

  return (
    <StyledQuestionForm>
      <HeaderContainer>
        <div className="top">
          <h1 className="headerTitle">Review your question</h1>
        </div>
        <div className="prompt">Please do a final review of your question and then post.</div>
      </HeaderContainer>
      <FormContainer>
        <TitleForm>
          <span className="title">Title</span>
          <span className="instruction">Be specific and imagine you’re asking a question to another person.</span>
          <TitleInput
            onChange={handleTitleChange}
            onBlur={handleTitleBlur}
            placeholder="e.g. Is there an R function for finding the index of an element in a vector?"
            $hasError={!isTitleValid}
          ></TitleInput>
          {!isTitleValid && <span className="inputError">Title must be at least 15 characters.</span>}
        </TitleForm>
        <BodyForm>
          <span className="title">Body</span>
          <span className="instruction">
            The body of your question contains your problem details and results. Minimum 220 characters.
          </span>
          <StyledReactQuill
            theme="snow"
            value={bodyValue}
            onChange={handleBodyChange}
            onBlur={handleBodyBlur}
            $hasError={!isBodyValid}
          />
          <div className="inputError">
            {!isBodyValid && <span className="inputError">Body must be at least 220 characters.</span>}
          </div>
        </BodyForm>

        <TagsForm>
          <span className="title">Tags</span>
          <span className="instruction">
            Add up to 5 tags to describe what your question is about. Start typing to see suggestions.
          </span>
          <TagsInput placeholder="e.g. (sql-server angularjs mysql)"></TagsInput>
        </TagsForm>
        <SubmitContainer>
          <Btn onClick={handleSubmit}>Post your question</Btn>
        </SubmitContainer>
      </FormContainer>
    </StyledQuestionForm>
  );
}

export default QuestionForm;

const StyledQuestionForm = styled.div`
  display: flex;
  flex-direction: column;
  width: 815px;
  min-height: 750px;
  padding: 0px 24px 24px 24px;
`;

const HeaderContainer = styled.div`
  display: flex;
  flex-direction: column;
  .top {
    display: flex;
    flex-direction: row;
    align-items: center;
    min-height: 130px;
  }
  .headerTitle {
    font-size: 26px;
    font-weight: 600;
  }
  .prompt {
    min-height: 50px;
    margin-top: 16px;
    margin-bottom: 24px;
    width: 100%;
    display: flex;
    align-items: center;
    background-color: rgb(235, 244, 251);
    border-radius: 6px;
    border: 1px solid rgb(166, 206, 237);
    padding: 16px;
    max-width: 815px;
  }
`;

const FormContainer = styled.div`
  min-height: 750px;
  max-width: 815px;
`;

const TitleForm = styled.div`
  display: flex;
  flex-direction: column;
  background-color: white;
  border-radius: 6px;
  padding: 24px;
  border: 1px solid rgb(214 217 220);
  .inputError {
    color: red;

    font-size: 12px;
  }
  .title {
    font-weight: 500;
    margin-bottom: 4px;
  }

  .instruction {
    font-size: 12px;
    margin-bottom: 8px;
  }
`;

const BodyForm = styled(TitleForm)`
  min-height: 520px;
  margin-top: 12px;
  .inputError {
    position: relative;
    top: 22px;
  }
`;

const TagsForm = styled(TitleForm)`
  margin-top: 12px;
`;

const SubmitContainer = styled.div`
  display: flex;
  flex-direction: row;
  margin-top: 12px;
  margin-bottom: 30px;
`;

const Btn = styled.button`
  height: 38px;
  padding: 10.5px;
  border-radius: 6px;
  border: none;
  white-space: nowrap;
  cursor: pointer;
  color: white;
  background-color: hsl(206, 100%, 52%);
`;

const Input = styled.input`
  padding-left: 10px;
  height: 30px;
  width: 100%;
  border-radius: 6px;
  font-size: 13px;
  margin-bottom: 2px;
  border-width: 1px;
  border-style: solid;
  border-color: ${(props) => (props.$hasError ? "red" : "#bbc0c4")};
  &:focus {
    border-color: ${(props) => (props.$hasError ? "red" : "hsl(206, 90%, 69.5%)")};
    box-shadow: ${(props) =>
      props.$hasError ? "0 0 0 4px rgba(255, 0, 0, 0.15)" : "0 0 0 4px hsla(206, 100%, 40%, 0.15)"};
    outline: none;
  }
  &::placeholder {
    color: rgba(0, 0, 0, 0.3);
  }
`;

const TitleInput = styled(Input)``;

const TagsInput = styled(Input)``;

const StyledReactQuill = styled(ReactQuill)`
  height: 360px;
  .ql-container {
    border-color: ${(props) => (props.$hasError ? "red" : "#bbc0c4")};
    border-radius: 0px 0px 6px 6px;
  }
  .ql-editor {
  }
  .ql-toolbar {
    background-color: rgb(251, 251, 251);
    border-bottom: none;
    border-color: ${(props) => (props.$hasError ? "red" : "#bbc0c4")};
    border-radius: 6px 6px 0px 0px;
  }
`;
