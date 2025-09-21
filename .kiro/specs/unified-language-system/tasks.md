# Implementation Plan

- [x] 1. Create unified language management system


  - Create UnifiedLanguageManager that combines LanguageManager and ContentManager functionality
  - Implement single source of truth for language state using StateFlow
  - Add persistence layer for unified language preferences
  - _Requirements: 1.1, 1.2, 4.3_

- [ ] 2. Implement language data models and interfaces
  - Create Language data class with code, displayName, flag, and isDefault properties
  - Define LanguageState data class for managing current state and translation status
  - Create LanguageEvent sealed class for handling language change events
  - _Requirements: 4.1, 4.2_

- [ ] 3. Create translation coordination system
  - Implement TranslationCoordinator interface to manage UI and content updates
  - Add methods for coordinating between locale changes and ML Kit translations
  - Implement notification system for ViewModels when language changes
  - _Requirements: 2.1, 2.2, 4.3_



- [ ] 4. Update repositories to use unified language system
  - Modify CharacterRepository to observe UnifiedLanguageManager instead of ContentManager
  - Update PlanetRepository to use unified language state


  - Remove ContentManager dependencies from repository classes
  - _Requirements: 1.2, 2.2, 4.1_

- [ ] 5. Update ViewModels for unified language management
  - Modify CharactersViewModel to observe UnifiedLanguageManager.currentLanguage


  - Update PlanetsViewModel to use unified language state
  - Update CharacterDetailViewModel to react to unified language changes
  - Remove ContentManager dependencies from all ViewModels
  - _Requirements: 2.1, 2.2, 2.3_

- [ ] 6. Redesign Settings screen for single language selection
  - Remove separate "UI Language" and "Content Language" sections
  - Create single "Language" section with unified language options
  - Update language selection UI to show native names and flag icons
  - Add current language selection indicator
  - _Requirements: 1.1, 5.1, 5.2, 5.3_

- [ ] 7. Implement loading states for translation process
  - Add translation loading indicators when switching to English


  - Create TranslatingContent component for showing translation progress
  - Implement graceful fallback when translation fails
  - Add appropriate user feedback during language changes
  - _Requirements: 3.1, 3.2, 3.3, 3.4_

- [ ] 8. Update MainActivity for unified language initialization
  - Replace separate LanguageManager and ContentManager initialization with UnifiedLanguageManager
  - Ensure proper cleanup of translation resources in onDestroy
  - Remove duplicate language-related initialization code
  - _Requirements: 1.4, 4.1_

- [ ] 9. Implement migration logic for existing users
  - Create migration function to convert existing separate language preferences to unified system
  - Ensure backward compatibility during transition period
  - Handle edge cases where users had different UI and content languages
  - Set appropriate default language when no previous preference exists
  - _Requirements: 1.4, 5.4_

- [ ] 10. Add comprehensive error handling and fallback mechanisms
  - Implement graceful degradation when ML Kit translation fails
  - Add retry mechanisms for failed translations
  - Handle network connectivity issues during translation
  - Ensure UI remains functional even if translation services are unavailable
  - _Requirements: 3.2, 3.3_

- [ ] 11. Create unit tests for unified language system
  - Write tests for UnifiedLanguageManager state management
  - Test language persistence and restoration functionality
  - Create tests for TranslationCoordinator coordination logic
  - Test Language data model validation and transformation






  - _Requirements: 4.2_

- [ ] 12. Implement integration tests for end-to-end language changes
  - Test complete flow from language selection to UI and content updates
  - Verify that ViewModels properly react to language changes
  - Test translation triggering and completion scenarios
  - Validate that all screens maintain language consistency
  - _Requirements: 2.1, 2.2, 2.3_

- [ ] 13. Remove deprecated language management components
  - Delete LanguageManager class and related files
  - Remove ContentManager class and dependencies
  - Clean up unused imports and references throughout codebase
  - Update documentation to reflect unified system
  - _Requirements: 4.1, 4.2_

- [ ] 14. Optimize performance and memory usage
  - Implement translation result caching to avoid repeated ML Kit calls
  - Optimize UI update batching during language changes
  - Add lazy loading for language resources
  - Monitor and optimize memory usage during translation processes
  - _Requirements: 2.1, 4.2_

- [ ] 15. Final testing and validation
  - Perform comprehensive manual testing of all language change scenarios
  - Validate that translation loading states work correctly
  - Test app behavior with poor network connectivity
  - Verify that language preferences persist correctly across app restarts
  - _Requirements: 1.1, 1.2, 2.1, 2.2, 3.1, 3.2_