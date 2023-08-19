import { styled } from "styled-components";
import { useNavigate } from "react-router-dom";

function SearchInfo () {

  const navigate = useNavigate();

  const searchAskBtnHandler = () => {
    navigate('/questionform');
  }
    return (
        <SearchInfoContainer>
            <PopoverArrow />
            <SearchHintsContainer>
                <HintsPartsContainer>
                    <HintsContainer>
                        <Hints>
                            <span>[tags]</span>
                            <span>search within a tag</span>
                        </Hints>
                        <Hints>
                            <span>user:1234</span>
                            <span>search by author</span>
                        </Hints>
                        <Hints>
                            <span>"words here"</span>
                            <span>exact phrase</span>
                        </Hints>
                        <Hints>
                            <span>collective:"Name"</span>
                            <span>collective content</span>
                        </Hints>
                    </HintsContainer>
                    <HintsContainer>
                        <Hints>
                            <span>answers:0</span>
                            <span>unanswered questions</span>
                        </Hints>
                        <Hints>
                            <span>score:3</span>
                            <span>posts with a 3+ score</span>
                        </Hints>
                        <Hints>
                            <span>is:question</span>
                            <span>type of post</span>
                        </Hints>
                        <Hints>
                            <span>isaccepted:yes</span>
                            <span>search within status</span>
                        </Hints>
                    </HintsContainer>
                </HintsPartsContainer>
                <BtnLinkContainer>
                    <BtnLinkElementContainer>
                        <AskBtnContainer>
                            <HeaderAskBtn
                              onClick={searchAskBtnHandler}>
                                Ask a question</HeaderAskBtn>
                        </AskBtnContainer>
                        <SearchHelpContiner>
                            Search Help
                        </SearchHelpContiner>
                    </BtnLinkElementContainer>
                </BtnLinkContainer>
            </SearchHintsContainer>
        </SearchInfoContainer>
    );
}

export default SearchInfo;

const SearchInfoContainer = styled.div`
  position: absolute;
  top: 0;
  margin: 0px;
  transform: translate(0px, 42px);
  width: 192px;
  min-width: 192px;
  max-width: 576px;
  width: 100%;
  padding: 0px;
  background-color: hsl(0,0%,100%);
  border: 1px solid hsl(210,8%,85%);
  box-shadow: 0 1px 3px hsla(0,0%,0%,0.06), 0 2px 6px hsla(0, 0%, 0%, 0.06), 0 3px 8px hsla(0, 0%, 0%, 0.09);
  border-radius: 6px;
  color: hsl(210,8%,5%);
  z-index: 2000;
  white-space: nowrap;
`

const PopoverArrow = styled.div`
  position: absolute;
  width: 12px;
  height: 12px;
  top: -6px;
  left: calc(50% - 8px);
  z-index: -1;
  transform: rotate(135deg);
  background-color: hsl(0,0%,100%);
  border: 1px solid hsl(210,8%,85%);
`

const SearchHintsContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  background-color: hsl(0,0%,100%);
  margin: 0;
  border-radius: 6px;
  width: 100%;
  min-width: 192px;
  max-width: 576px;
  white-space: nowrap;
`

const HintsPartsContainer = styled.div`
    display: flex;
    padding: 12px;
    width: 100%;
`

const HintsContainer = styled.div`
    display: block;
    flex-basis: 50%;
    white-space: normal;
    width: 100%;
`

const Hints = styled.div`
    display: block;
    margin-bottom: 12px;
    width: 100%;
    white-space: normal;

    > span:nth-child(1) {
        font-size: 14px;
        padding-right: 4px;
    }

    > span:nth-child(2) {
        color: hsl(210,8%,45%);
        font-size: 12px;
    }
`

const BtnLinkContainer = styled.div`
  border-top: 1px solid hsl(210,8%,90%);
  padding: 12px;
`

const BtnLinkElementContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`

const AskBtnContainer = styled.div`
`

const HeaderAskBtn = styled.button`
  padding: 8px 0.8em;
  border-radius: 6px;
  border: none;
  white-space: nowrap;
  cursor: pointer;
  color: hsl(205,47%,42%);
  background-color: hsl(205,46%,92%);
  font-size: 11px;

  &:hover {
    background-color: hsl(205,57%,81%);
  }

  &:active {
    background-color: hsl(205,56%,76%);
  }

  &:focus {
    box-shadow: 0 0 0 4px hsla(206, 100%, 40%, 0.15);
    outline: none;
  }
`

const SearchHelpContiner = styled.div`
  font-size: 11px;
  color: hsl(206,100%,40%);
`