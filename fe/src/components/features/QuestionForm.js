import styled from "styled-components";
import { useState } from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

function QuestionForm() {
  const [value, setValue] = useState("");

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
          <TitleInput placeholder="e.g. Is there an R function for finding the index of an element in a vector?"></TitleInput>
        </TitleForm>
        <BodyForm>
          <span className="title">Body</span>
          <span className="instruction">
            The body of your question contains your problem details and results. Minimum 220 characters.
          </span>
          <ReactQuill theme="snow" value={value} onChange={setValue} />
          <BodyInput></BodyInput>
        </BodyForm>
        <TagsForm>
          <span className="title">Tags</span>
          <span className="instruction">
            Add up to 5 tags to describe what your question is about. Start typing to see suggestions.
          </span>
          <TagsInput placeholder="e.g. (sql-server angularjs mysql)"></TagsInput>
        </TagsForm>
        <SubmitContainer>
          <Btn>Post your question</Btn>
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
  margin-top: 12px;
`;

const TagsForm = styled(TitleForm)`
  margin-top: 12px;
`;

const SubmitContainer = styled.div`
  display: flex;
  flex-direction: row;
  margin-top: 12px;
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
  border: 1px solid #bbc0c4;
  &:focus {
    border-color: hsl(206, 90%, 69.5%);
    box-shadow: 0 0 0 4px hsla(206, 100%, 40%, 0.15);
    outline: none;
  }
  &::placeholder {
    color: rgba(0, 0, 0, 0.3);
  }
`;

const TitleInput = styled(Input)``;
const BodyInput = styled(Input)`
  min-height: 260px;
`;
const TagsInput = styled(Input)``;
