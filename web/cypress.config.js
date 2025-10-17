
import { defineConfig } from 'cypress';
import createBundler from '@bahmutov/cypress-esbuild-preprocessor';
import { addCucumberPreprocessorPlugin } from '@badeball/cypress-cucumber-preprocessor';
import { createEsbuildPlugin } from '@badeball/cypress-cucumber-preprocessor/esbuild';
import allureWriter from '@shelex/cypress-allure-plugin/writer.js';

export default defineConfig({
  e2e: {
    specPattern: [
      'cypress/e2e/**/*.cy.js',
      'cypress/e2e/features/**/*.feature'
    ],
    supportFile: 'cypress/support/e2e.js',
    baseUrl: 'https://www.saucedemo.com',
    pageLoadTimeout: 60000,
    defaultCommandTimeout: 10000,
    requestTimeout: 10000,
    responseTimeout: 10000,
    viewportWidth: 1280,
    viewportHeight: 720,
    video: false,
    screenshotOnRunFailure: true,
    setupNodeEvents(on, config) {
      const bundler = createBundler({
        plugins: [createEsbuildPlugin(config)],
      });
      
      on('file:preprocessor', bundler);
      
      // Só adiciona cucumber se o arquivo for .feature
      on('file:preprocessor', (file) => {
        if (file.filePath.includes('.feature')) {
          addCucumberPreprocessorPlugin(on, config);
        }
        return bundler(file);
      });
      
      allureWriter(on, config);
      
      return config;
    }
  },
  env: {
    allure: true
  }
});
