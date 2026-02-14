# Autonomy Scenarios Answers

This document outlines my approach to common challenges faced when creating technical documentation and codelabs.

---

## Scenario 1: Undocumented API Behavior

**1. Determining Behavior:** Since the API team is unavailable, I would take a "black-box testing" approach. Using tools like **Postman** or **cURL**, I would systematically send malformed JSON, missing required fields, and invalid data types to the endpoint to observe the HTTP status codes and error bodies returned. Additionally, I would examine the **source code** of the API (if accessible) to identify validation logic or exception handlers that define the behavior for invalid inputs.

**2. Helping Developers:** I would include a dedicated section or "Note" block in the codelab titled "Handling Validation Errors." This would provide examples of common error responses (e.g., 400 Bad Request) and explain the specific validation constraints (e.g., "Field 'price' must be a positive number"). I would also guide the developer on how to interpret the API's error messages to debug their requests effectively.

**3. Documentation for Future Reference:** I would create a temporary "API Discovery" document or a README in the repository's `internal` folder detailing my findings. Once the API team returns, I would share this draft with them to verify accuracy and ensure it gets integrated into the official documentation.

---

## Scenario 2: Conflicting Information

**1. Determining the Recommended Method:** I would prioritize the **official framework documentation (Method A)** as the primary source of truth for stability, but I would cross-reference it with the **internal tech talk (Method B)** to see if Method A has been deprecated internally for security or architectural reasons. Method C (the template) would be my last resort as templates can often become legacy debt.

**2. Decision Criteria:** My decision would be based on:
* **Maintainability:** Which method is the current industry standard?
* **Security:** Does one method offer better protection?
* **Consistency:** Which method aligns best with our current internal stack and future roadmap?
* **Simplicity:** Which method is easiest for a developer to understand in a tutorial context?

**3. Proceeding Without a Definitive Answer:** I would implement the most modern and secure method (likely B or A) and add a "Design Note" in the codelab. This note would acknowledge the existence of alternative methods and briefly explain why the chosen method was selected for this specific tutorial, ensuring transparency for senior developers.

---

## Scenario 3: Last-Minute Breaking Changes

**1. Immediate Action Plan:** I would immediately notify the project lead that a critical update is required. I would then create a new feature branch to implement the version bump to 5.20.0 and begin auditing the affected steps.

**2. Efficient Identification of Changes:** I would use a **Diff tool** or Git to compare the `solution` code of my codelab against the new framework's migration guide or changelog. By updating the `pom.xml` and running a build, the compiler errors will quickly point me to the exact lines of code that the breaking changes affected.

**3. Verification with Limited Time:** I would focus on the **"Happy Path"**. I would update the `solution` branch first, ensure it compiles and passes its unit tests, and then manually walk through the updated codelab steps one last time to ensure the written instructions match the new code reality.

**4. Stakeholder Communication:** I would communicate with stakeholders **immediately**. I would provide a brief status update: "Identifying breaking changes for v5.20.0; fix in progress. Expect a 2-hour delay for publication." Transparency is better than publishing broken code. Once verified, I would send a final "Ready for Publish" confirmation.