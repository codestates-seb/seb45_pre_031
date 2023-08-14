import { styled } from "styled-components";

const SearchInfoContainer = styled.div`
    position: absolute;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    background-color: hsl(0,0%,100%);
    border: 1px solid hsl(210,8%,85%);
    box-shadow: 0 1px 3px hsla(0,0%,0%,0.06), 0 2px 6px hsla(0, 0%, 0%, 0.06), 0 3px 8px hsla(0, 0%, 0%, 0.09);
    width: 640px;
    height: 184px;
    border-radius: 6px;
    color: hsl(210,8%,5%);
    font-size: 15px;
    white-space: normal;
    top: 42px;
    left: 0;
    right: 0;

    &::before {
        content: '';
        border-top: 0px solid transparent;
        border-right: 12px solid transparent;
        border-bottom: 18px solid white;
        border-left: 12px solid transparent;
        position: absolute;
        top: -13px;
        left: 300;
    }
`

const InfoListContainer = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
`

const InfoElementGroup = styled.div`
    display: flex;
    flex-direction: column;
    gap: 8px;
`

const ElementContainer = styled.div`

    > span:nth-child(1) {
        padding-right: 6px;
    }

    > span:nth-child(2) {
        font-size: 13px;
        color: hsl(210,8%,45%);
    }
`

const BtnLinkContainer = styled.div`
`

function SearchInfo () {
    return (
        <SearchInfoContainer>
            <InfoListContainer>
                <InfoElementGroup>
                    <ElementContainer>
                        <span>[tag]</span>
                        <span>search within a tag</span>
                    </ElementContainer>
                    <ElementContainer>
                        <span>user:1234</span>
                        <span>unanswered questions</span>
                    </ElementContainer>
                    <ElementContainer>
                        <span>"words here"</span>
                        <span>exact phrase</span>
                    </ElementContainer>
                    <ElementContainer>
                        <span>collective:"Name"</span>
                        <span>collective content</span>
                    </ElementContainer>
                 </InfoElementGroup>
                 <InfoElementGroup>
                    <ElementContainer>
                        <span>answers:0</span>
                        <span>unanswered questions</span>
                    </ElementContainer>
                    <ElementContainer>
                        <span>score:3</span>
                        <span>posts with a 3+ score</span>
                    </ElementContainer>
                    <ElementContainer>
                        <span>is:question</span>
                        <span>type of post</span>
                    </ElementContainer>
                    <ElementContainer>
                        <span>isaccepted:yes</span>
                        <span>search within status</span>
                    </ElementContainer>
                 </InfoElementGroup>
            </InfoListContainer>
            <BtnLinkContainer>
                <div>
                    <button>Ask a question</button>
                    <span>Search help</span>
                </div>
            </BtnLinkContainer>
        </SearchInfoContainer>
    );
}

export default SearchInfo;