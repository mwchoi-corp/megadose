## 브리핑 생성 워크플로우

1. **프롬프트 입력**

2. **프롬프트에서 검색 내용 추출**
   - **검색 내용 규격**
        ```json
        {
          "keywords": [
            "AI",
            "머신러닝",
            "자연어 처리"
          ],
          "competitors": [
            "OpenAI",
            "Google DeepMind",
            "Anthropic"
          ],
          "reference_urls": [
            "https://www.example.com/ai-report-2023",
            "https://www.another-example.org/ml-trends"
          ],
          "report_scope": [
            "AI 기술 동향",
            "경쟁사 분석",
            "시장 예측"
          ],
          "search_urls": [
            "https://www.google.com/search?q=AI+기술+동향",
            "https://www.bing.com/search?q=머신러닝+최신+연구"
          ]
        }
        ```

3. **웹 데이터 수집**
   - **작업**
     - 관련 웹사이트에서 키워드 기반 정보 수집

4. **웹 데이터 전처리 및 저장**
   - **작업**
     - 데이터 전처리
     - 원문, 요약본 DB/vectorDB 저장
   -  **웹 데이터 규격**
       ```json
       [
        {
          "id": "1a2b3c4d5e",
          "title": "2024년 인공지능 기술 트렌드 보고서",
          "content": "2024년 인공지능 기술은 챗GPT와 같은 생성형 AI의 발전이 주도하고 있습니다. 특히, 멀티모달 AI, 온디바이스 AI, AI 윤리 및 책임에 대한 논의가 활발히 진행되고 있으며, 산업 전반에 걸쳐 AI 도입이 가속화되고 있습니다.",
          "url": "https://www.example-ai-report.com/trends-2024",
          "created_at": "2024-09-08T10:00:00Z"
        },
        {
          "id": "6f7g8h9i0j",
          "title": "파이썬 웹 개발 프레임워크 비교: Django vs Flask",
          "content": "웹 개발자들에게 인기 있는 파이썬 프레임워크인 Django와 Flask를 비교 분석한 문서입니다. Django는 '배터리가 포함된' 풀스택 프레임워크로, 대규모 프로젝트에 적합하며, Flask는 마이크로 프레임워크로 가볍고 유연하여 소규모 프로젝트나 API 개발에 유리합니다.",
          "url": "https://www.webdev-guide.com/python-frameworks",
          "created_at": "2024-09-07T15:30:00Z"
        },
        {
          "id": "1k2l3m4n5o",
          "title": "클라우드 컴퓨팅 시장 전망 및 주요 기업 동향",
          "content": "클라우드 컴퓨팅 시장은 아마존 AWS, 마이크로소프트 Azure, 구글 클라우드 플랫폼(GCP)이 삼분하고 있습니다. 최근에는 하이브리드 클라우드와 엣지 컴퓨팅 기술이 부상하며, 기업들의 클라우드 활용 전략이 더욱 다양해지고 있습니다.",
          "url": "https://www.tech-insights.net/cloud-market-outlook",
          "created_at": "2024-09-06T09:45:00Z"
        }
      ]
       ```

5. **브리핑 목차(문단 주제) 구성**

6. **브리핑 목차별로 내용 작성**
   - **작업**
     - 각 목차별 vectorDB 조회하여 내용 작성

7. **문단 취합 및 검증**
   - **작업**
     - 문맥, 논리성, 출처 및 최종 브리핑 내용 검토

## 사용 기술스택
- java
- openai api
- sqlite 로컬 파일 기반 DB
- ChromaDB (RAG)
