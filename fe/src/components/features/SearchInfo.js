import { styled } from "styled-components";

const SearchInfoContainer = styled.div`
    position: absolute;
    inset: 0px auto auto 0px;
    margin: 0px;
    transform: translate(0px, 42px);
    min-width: 12rem;
    max-width: 36rem;
    width: 100%;
    padding: 0px;
    background-color: hsl(0,0%,100%);
    border: 1px solid hsl(210,8%,85%);
    box-shadow: 0 1px 3px hsla(0,0%,0%,0.06), 0 2px 6px hsla(0, 0%, 0%, 0.06), 0 3px 8px hsla(0, 0%, 0%, 0.09);
    border-radius: 6px;
    color: hsl(210,8%,5%);
    z-index: 2000;
    white-space: normal;
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
    background-color: hsl(0,0%,100%);
    margin: 0;
    border-radius: 6px;
`

const HintsPartsContainer = styled.div`
    display: flex;
    padding: 12px;
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
`

const BtnLinkElementContainer = styled.div`
`

const AskBtnContainer = styled.div`
`

const SearchHelpContiner = styled.div`
`

function SearchInfo () {
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

                        </AskBtnContainer>
                        <SearchHelpContiner>

                        </SearchHelpContiner>
                    </BtnLinkElementContainer>
                </BtnLinkContainer>
            </SearchHintsContainer>
        </SearchInfoContainer>
    );
}

export default SearchInfo;