# 브리핑 생성 워크플로우

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

5. **브리핑 목차(문단 주제) 구성**

6. **브리핑 목차별로 내용 작성**
   - **작업**
     - 각 목차별 vectorDB 조회하여 내용 작성

7. **문단 취합 및 검증**
   - **작업**
     - 문맥, 논리성, 출처 및 최종 브리핑 내용 검토
