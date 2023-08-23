import styled from "styled-components";

function TagList() {
  return (
    <StyledTagList>
      <h2>Tags</h2>
      <span className="inst">
        A tag is a keyword or label that categorizes your question with other, similar questions. Using the right tags
        makes it easier for others to find and answer your question.
      </span>
      <span className="blue">Show all tag synonyms</span>
      <div>
        <FilterContainer>
          <Filter>
            <FilterOption>Popular</FilterOption>
            <FilterOption>Name</FilterOption>
            <FilterOption>New</FilterOption>
          </Filter>
        </FilterContainer>
        <ContentsContainer>
          <TagLine>
            <TagContainer>
              <Tag>javascript</Tag>
              <TagDescription>
                For questions about programming in ECMAScript (JavaScript/JS) and its different dialects/implementations
                (except for ActionScript)...
              </TagDescription>
              <TagInfo>2509364 questions</TagInfo>
            </TagContainer>
            <TagContainer>
              <Tag>python</Tag>
              <TagDescription>
                Python is a dynamically typed, multi-purpose programming language. It is designed to be quick to learn,
                understand, and use, and enforces...
              </TagDescription>
              <TagInfo>2159346 questions</TagInfo>
            </TagContainer>
            <TagContainer>
              <Tag>java</Tag>
              <TagDescription>
                Java is a high-level object-oriented programming language. Use this tag when you're having problems
                using or understanding the language...
              </TagDescription>
              <TagInfo>1905142 questions</TagInfo>
            </TagContainer>
            <TagContainer>
              <Tag>c#</Tag>
              <TagDescription>
                C# (pronounced "see sharp") is a high-level, statically typed, multi-paradigm programming language
                developed by Microsoft...
              </TagDescription>
              <TagInfo>1600359 questions</TagInfo>
            </TagContainer>
          </TagLine>
          <TagLine>
            <TagContainer>
              <Tag>php</Tag>
              <TagDescription>
                PHP is an open source, multi-paradigm, dynamically-typed and interpreted scripting language designed
                initially for server-side...
              </TagDescription>
              <TagInfo>1461394 questions</TagInfo>
            </TagContainer>
            <TagContainer>
              <Tag>android</Tag>
              <TagDescription>
                Android is Google's mobile operating system, used for programming or developing digital devices
                (Smartphones, Tablets, Automobiles...
              </TagDescription>
              <TagInfo>1409005 questions</TagInfo>
            </TagContainer>
            <TagContainer>
              <Tag>html</Tag>
              <TagDescription>
                HTML (HyperText Markup Language) is the markup language for creating web pages and other information to
                be displayed in a web browser...
              </TagDescription>
              <TagInfo>1179579 questions</TagInfo>
            </TagContainer>
            <TagContainer>
              <Tag>jquery</Tag>
              <TagDescription>
                jQuery is a JavaScript library. Consider also adding the JavaScript tag. jQuery is a popular
                cross-browser JavaScript library that facilitates Document Obje...
              </TagDescription>
              <TagInfo>1034428 questions</TagInfo>
            </TagContainer>
          </TagLine>
          <TagLine>
            <TagContainer>
              <Tag>c++</Tag>
              <TagDescription>
                C++ is a general-purpose programming language. Initially, it was designed as an extension to C and has a
                similar syntax, but it is...
              </TagDescription>
              <TagInfo>798301 questions</TagInfo>
            </TagContainer>
            <TagContainer>
              <Tag>css</Tag>
              <TagDescription>
                CSS (Cascading Style Sheets) is a representation style sheet language used for describing the look and
                formatting of HTML (HyperText
              </TagDescription>
              <TagInfo>797149 questions</TagInfo>
            </TagContainer>
            <TagContainer>
              <Tag>ios</Tag>
              <TagDescription>
                iOS is the mobile operating system running on the Apple iPhone, iPod touch, and iPad. Use this tag [ios]
                for questions related to programming on
              </TagDescription>
              <TagInfo>682950 questions</TagInfo>
            </TagContainer>
            <TagContainer>
              <Tag>sql</Tag>
              <TagDescription>
                Structured Query Language (SQL) is a language for querying databases. Questions should include code
                examples, table structure, sample...
              </TagDescription>
              <TagInfo>664596 questions</TagInfo>
            </TagContainer>
          </TagLine>
          <TagLine>
            <TagContainer>
              <Tag>mysql</Tag>
              <TagDescription>
                MySQL is a free, open-source Relational Database Management System (RDBMS) that uses Structured Query
                Language (SQL). DO NOT...
              </TagDescription>
              <TagInfo>661131 questions</TagInfo>
            </TagContainer>
            <TagContainer>
              <Tag>r</Tag>
              <TagDescription>
                R is a free, open-source programming language & software environment for statistical computing,
                bioinformatics...
              </TagDescription>
              <TagInfo>495925 questions</TagInfo>
            </TagContainer>
            <TagContainer>
              <Tag>node.js</Tag>
              <TagDescription>
                Node.js is an event-based, non-blocking, asynchronous I/O runtime that uses Google's V8 JavaScript
                engine and libuv library. It is used...
              </TagDescription>
              <TagInfo>466412 questions</TagInfo>
            </TagContainer>
            <TagContainer>
              <Tag>reactjs</Tag>
              <TagDescription>
                React is a JavaScript library for building user interfaces. It uses a declarative, component-based
                paradigm and aims to be efficient...
              </TagDescription>
              <TagInfo>464557 questions</TagInfo>
            </TagContainer>
          </TagLine>
        </ContentsContainer>
      </div>
    </StyledTagList>
  );
}

export default TagList;

const StyledTagList = styled.div`
  padding: 20px 20px 20px 30px;
  display: flex;
  flex-direction: column;
  border-left: 1px solid rgb(214, 217, 220);
  width: 1060px;
  h2 {
    font-weight: 400;
    margin-bottom: 20px;
  }
  .inst {
    width: 660px;
    font-size: 15px;
  }
  .blue {
    font-size: 14px;
    color: rgb(0, 99, 191);
    margin-top: 10px;
  }
`;
const FilterContainer = styled.div`
  padding-right: 20px;
  display: flex;
  justify-content: flex-end;
`;
const Filter = styled.div`
  display: flex;
  flex-direction: row;
  border: 1px solid rgb(214, 217, 220);
  border-radius: 6px;
  height: 36px;
`;
const FilterOption = styled.div`
  display: flex;
  font-size: 12px;
  align-items: center;
  padding: 10px;
  border-right: 1px solid rgb(214, 217, 220);
  &:last-of-type {
    border-right: none;
  }
  &:hover {
    background-color: hsl(210, 8%, 97.5%);
  }
`;

const ContentsContainer = styled.div`
  margin-top: 16px;
  margin-bottom: 20px;
  height: 800px;
  display: flex;
  flex-direction: column;
`;
const TagLine = styled.div`
  display: flex;
  flex-direction: row;
`;
const TagContainer = styled.div`
  padding: 16px;
  border-radius: 6px;
  border: 1px solid rgb(214, 217, 220);
  width: 240.5px;
  height: 160px;
  margin-right: 12px;
  margin-bottom: 12px;
`;

const Tag = styled.span`
  width: auto;
  margin-right: 4px;
  font-size: 13px;
  color: hsl(206, 100%, 40%);
  background-color: hwb(205deg 88.32% 4.32%);
  border-radius: 6px;
  padding: 4px 8px 4px 8px;
  &:hover {
    color: hsl(205, 46%, 32%);
    background-color: hsl(205deg 53% 88%);
  }
`;
const TagDescription = styled.div`
  margin-top: 16px;
  font-size: 12px;
  height: 70px;
  overflow: visible;
`;
const TagInfo = styled.div`
  margin-top: 10px;
  color: rgb(161, 161, 161);
  font-size: 12px;
`;
